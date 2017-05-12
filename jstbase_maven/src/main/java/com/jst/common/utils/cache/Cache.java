package com.jst.common.utils.cache;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;

import com.sun.faces.util.FacesLogger;

    /**
     * A concurrent caching mechanism.
     */
public class Cache<K, V> {

            // Log instance for this class
    private static final Logger LOGGER = FacesLogger.UTIL.getLogger();


        /**
     * Factory interface for creating various cacheable objects.
     */
    public interface Factory<K,V> {

        V newInstance(final K arg) throws InterruptedException;

    } // END Factory


        private ConcurrentMap<K,Future<V>> cache =
              new ConcurrentHashMap<K,Future<V>>();
        private Factory<K,V> factory;


        // -------------------------------------------------------- Constructors


        /**
         * Constructs this cache using the specified <code>Factory</code>.
         * @param factory
         */
        public Cache(Factory<K,V> factory) {

            this.factory = factory;

        }


        // ------------------------------------------------------ Public Methods


        /**
         * If a value isn't associated with the specified key, a new
         * {@link java.util.concurrent.Callable} will be created wrapping the <code>Factory</code>
         * specified via the constructor and passed to a {@link java.util.concurrent.FutureTask}.  This task
         * will be passed to the backing ConcurrentMap.  When {@link java.util.concurrent.FutureTask#get()}
         * is invoked, the Factory will return the new Value which will be cached
         * by the {@link java.util.concurrent.FutureTask}.
         *
         * @param key the key the value is associated with
         * @return the value for the specified key, if any
         */
        public V get(final K key) {

            while (true) {
                Future<V> f = cache.get(key);
                if (f == null) {
                    Callable<V> callable = new Callable<V>() {
                        public V call() throws Exception {
                            return factory.newInstance(key);
                        }
                    };
                    FutureTask<V> ft = new FutureTask<V>(callable);
                    f = cache.putIfAbsent(key, ft);
                    if (f == null) {
                        f = ft;
                        ft.run();
                    }
                }
                try {
                    return f.get();
                } catch (CancellationException ce) {
                    if (LOGGER.isLoggable(Level.FINEST)) {
                        LOGGER.log(Level.FINEST,
                                   ce.toString(),
                                   ce);
                    }
                    cache.remove(key);
                } catch (InterruptedException ie) {
                    if (LOGGER.isLoggable(Level.FINEST)) {
                        LOGGER.log(Level.FINEST,
                                   ie.toString(),
                                   ie);
                    }
                    cache.remove(key);
                } catch (ExecutionException ee) {
                    throw new FacesException(ee);
                }
            }

        }
        public V remove(final K key) {
            Future<V> t = cache.remove(key);
            V result = null;

            if (null != t) {
                try {
                    result = t.get();
                } catch (CancellationException ce) {
                    if (LOGGER.isLoggable(Level.FINEST)) {
                        LOGGER.log(Level.FINEST,
                                ce.toString(),
                                ce);
                    }
                } catch (InterruptedException ie) {
                    if (LOGGER.isLoggable(Level.FINEST)) {
                        LOGGER.log(Level.FINEST,
                                ie.toString(),
                                ie);
                    }
                } catch (ExecutionException ee) {
                    throw new FacesException(ee);
                }
            }

            return result;
    }

    } // END Cache
