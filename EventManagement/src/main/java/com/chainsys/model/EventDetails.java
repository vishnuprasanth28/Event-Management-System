package com.chainsys.model;

public class EventDetails {
	 
	    
	    private int typeId;
	    private boolean includesFood;
	    private boolean includesPhotography;
	    private boolean includesDecoration;
	    private String typeName;
		private int eventId;
		
		public EventDetails(int eventId, int typeId, boolean includesFood, boolean includesPhotography,
				boolean includesDecoration, String typeName) {
			
			this.eventId = eventId;
			this.typeId = typeId;
			this.includesFood = includesFood;
			this.includesPhotography = includesPhotography;
			this.includesDecoration = includesDecoration;
			this.typeName = typeName;
		}
		public EventDetails() {
			
		}
		
		
		@Override
		public String toString() {
			return "EventDetails [eventId=" + eventId + ", typeId=" + typeId
					+ ", includesFood=" + includesFood + ", includesPhotography=" + includesPhotography
					+ ", includesDecoration=" + includesDecoration + ", typeName=" + typeName + "]";
		}
		public int getEventId() {
			return eventId;
		}
		public void setEventId(int eventId) {
			this.eventId = eventId;
		}
		public int getTypeId() {
			return typeId;
		}
		public void setTypeId(int typeId) {
			this.typeId = typeId;
		}
		public boolean isIncludesFood() {
			return includesFood;
		}
		public void setIncludesFood(boolean includesFood) {
			this.includesFood = includesFood;
		}
		public boolean isIncludesPhotography() {
			return includesPhotography;
		}
		public void setIncludesPhotography(boolean includesPhotography) {
			this.includesPhotography = includesPhotography;
		}
		public boolean isIncludesDecoration() {
			return includesDecoration;
		}
		public void setIncludesDecoration(boolean includesDecoration) {
			this.includesDecoration = includesDecoration;
		}
		
		public String getTypeName() {
			return typeName;
		}
		public void setTypeName(String typeName) {
			this.typeName = typeName;
		}

}
