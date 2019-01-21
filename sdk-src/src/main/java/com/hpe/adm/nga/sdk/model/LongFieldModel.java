package com.hpe.adm.nga.sdk.model;

/**
 *
 * This class hold the LongFieldModel objects and serve as a long type FieldModel data holder 
 *
 *
 */
public class LongFieldModel implements FieldModel<Long> {
	
	//Private 
		private String name = "";
		private Long value = 0L;
		
		/**
		 * Creates a new LongFieldModel object
		 * 
		 * @param newName - Field name
		 * @param newValue - Field Value
		 */
		public LongFieldModel(String newName,Long newValue){
			
			setValue(newName,newValue);
		}
		
		/**
		 * get value
		 */
		public Long getValue()	{
			return value;
		}

    /**
		 * get Field's name
		 */
		public String getName(){
			return name;
		}
		
		/**
		 * set Field's name/value
		 */
		public void setValue(String newName,Long newValue){
			
			name = newName;
			value = newValue;
		}


}
