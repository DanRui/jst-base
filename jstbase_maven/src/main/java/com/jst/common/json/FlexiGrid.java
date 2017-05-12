package com.jst.common.json;

import java.util.ArrayList;
import java.util.List;

public class FlexiGrid {
	
	//默认的cell数目
	private final int  DEFAULT_CELL_COUNT= 10; 
	//总行数
	private    long     rowCount ;
	//当前页数
	private    int    page   ;
	
	private int pageSize=30 ;
	//行
	private  List<Row> rows = new ArrayList<Row>(pageSize) ;
	//cell 数
	private int cellCountOfRow= DEFAULT_CELL_COUNT ;
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setRowCount(long rowCount){
		this.rowCount = rowCount ;
	}
	
	public void setPage(int page){
		this.page = page ;
		
	}

	public void AddRow(Row row ){
		//将实际cell数目赋给cellCountOfRow 以节约内存空间.
		cellCountOfRow = row.cells.size() ;
		rows.add(row);		
	}
	
   
	public Row createRowInstance(){
		
		return new Row(); 
	}
		
	@Override
	public String  toString(){
		
		StringBuilder sb = new StringBuilder(pageSize*(cellCountOfRow+1)+20) ;
		
		sb.append("{page:").append(page);
		sb.append(",total:").append(rowCount) .append(",rows:[");
		for(Row row :rows){
			sb.append(row.toString()).append(",");		
		}
		
		//如果最后一位是",",去掉最后面的","
		String result=sb.toString();
		if(result.substring(result.length()-1,result.length()).equals(","))
		 result =result.substring(0, result.length()-1);
		
		result = result + "]}";

		
		return result ;
		
	}
		
	public class Row{
			
		private  Object id ;
		private List cells = new ArrayList(cellCountOfRow); 
			
		public void setId(Object id) {
			this.id = id;
		}

		public <U>  Row  addCell(U cell){	
			 if(cell==null){
				 cells.add("");
			 }else{
				 
				 cells.add(cell) ; 	
			 }
			  return this ;
		}
		@Override
		public String toString(){
			StringBuilder result = new StringBuilder(cellCountOfRow+10);
			result.append("{id:'").append(id).append("',");
			result.append("cell:[");
			for(int i=0;i<cells.size()-1;i++){
				result.append("'").append(cells.get(i)).append("',");
				
			}
			if(cells.size()>0){
				result.append("'").append(cells.get(cells.size()-1)).append("'");
			}
			result.append("]}");
			
			return result.toString();
			
		}
			
	}
	
	
	

}
