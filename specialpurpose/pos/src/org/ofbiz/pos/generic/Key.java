package org.ofbiz.pos.generic;


public class Key implements Comparable<Key>  
{  
    public String _key;
    public String _id; 
    public String _name; 
      
    public Key(String id, String name)  
    {  
        _key = id + name;  
        _id = id;
        _name = name;
    }  
      
    @Override  
    public int compareTo(Key other)  
    {  
        return _key.compareTo(other._key);  
    }  
      
    @Override  
    public boolean equals(Object other)  
    {  
        return (other != null) && (getClass() == other.getClass()) &&   
            _key.equals(((Key)other)._key);  
    }  
      
    @Override  
    public int hashCode()  
    {  
        return _key.hashCode();  
    }  
      
    @Override  
    public String toString()  
    {  
        return _key;  
    }  
}
