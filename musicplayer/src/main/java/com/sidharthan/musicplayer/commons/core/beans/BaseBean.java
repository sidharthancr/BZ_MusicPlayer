package com.sidharthan.musicplayer.commons.core.beans;

import java.io.Serializable;

/**
 * Base bean class for every Plain Old Java Object (POJO) definition.
 * 
 * @author sidharthan.r
 */

public class BaseBean implements Serializable {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final BaseBean other = (BaseBean) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	/** Serial id of this bean class. */
	private static final long serialVersionUID = 2961218811501806874L;

	/** Name used to identify the bean. */
	private String name;

	/** Identity of any bean class. */
	private String id;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns this bean's identity in Name:<name>, version:<version>, Id:<id>
	 * format.
	 * 
	 * @return String
	 */

	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("Name :" + name + " ");
		buffer.append("Id :" + id);
		return buffer.toString();
	}

}
