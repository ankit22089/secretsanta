package com.java.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AlienShuffled {
	@Id
	private int aid;
	private int child;
	public int getChild() {
		return child;
	}
	public void setChild(int child) {
		this.child = child;
	}
	public int getAid() {
		return aid;
	}
	public void setAid(int aid) {
		this.aid = aid;
	}
}
