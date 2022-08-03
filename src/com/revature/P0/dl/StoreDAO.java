package com.revature.P0.dl;

import java.util.ArrayList;

import com.revature.P0.models.Store;

public class StoreDAO implements DAO<Store>{
	@Override
	public void addInstance(Store newInstance) {
		TempStorage.stores.add(newInstance);
	}
	@Override
	public ArrayList<Store> getAllInstances(){
		return TempStorage.stores;
	}
	public void updateStore(Store store) {
		for(int i=0;i<TempStorage.stores.size();i++) {
			Store savedStore = TempStorage.stores.get(i);
			if (savedStore.name == store.name) {
				TempStorage.stores.set(i, store);
			}
		}
	}
}
