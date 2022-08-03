package com.revature.P0.dl;

import java.util.ArrayList;

public interface DAO<T> {
	void addInstance(T newInstance);
	ArrayList<T> getAllInstances(); 
}
