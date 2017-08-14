
// ~ Package Declaration
// ==================================================

package com.wing.utils.cache;

// ~ Comments
// ==================================================

public interface DataCache {

	// ~ Static Fields
	// ==================================================

	// ~ Methods
	// ==================================================

	public boolean put(String key, Object value);
	
	public Object get(String key);
	
	public boolean remove(String key);
}
