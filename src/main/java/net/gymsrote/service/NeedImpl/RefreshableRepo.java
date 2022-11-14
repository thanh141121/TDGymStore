package net.gymsrote.service.NeedImpl;

public interface RefreshableRepo<T> {
	
	void refresh(T t);
	
}
