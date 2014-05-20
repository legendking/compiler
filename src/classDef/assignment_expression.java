package classDef;
import java.util.Vector;

import core.first;
import classes.Tint;
import classes.pointer;
import classes.struct;
import classes.type;

public class assignment_expression extends root
{
	public assignment_expression(){}
	public int check() throws Exception
	{
		Vector<quad> v = new Vector<quad>();
		if (child.size() != 1)
		{
			root son = child.get(0);
			if (son.check()!=0) 
			{
				throw new Exception();
				//return 1; 
			}
			if (!((returnrecord)son.record).lvalue) 
			{
				throw new Exception();
				//return 1;
			}
			returnrecord r = (returnrecord)son.record;
			type t = r.rtype;
			r.constant = false;
			
			if (child.get(2).check()!=0) 
			{
				throw new Exception();
				//return 1;
			}
			
			if (((String)child.get(1).record).equals("="))
			{
				//System.out.println(t.typename);
				if (checkconvert(((returnrecord)child.get(2).record).rtype,t)!=0)throw new Exception();

				if(((returnrecord)child.get(2).record).constant)
				{
					location l = new location(0,"const",0,false,false);
					l.contain = ((returnrecord)child.get(2).record).value;
					location tmp = new temp(1);
					/*if(r.loc.address)v.add(new quad("store",l,null,r.loc));
					else v.add(new quad("load",l,null,r.loc));*/
					
					//--------------------------------------------//
					v.add(new quad("li",tmp,null,l));
					if(r.loc.address)
					{
						location tmp2 = new temp(2);
						v.add(new quad("load",tmp2,null,r.loc));
						v.add(new quad("sal",tmp,null,tmp2));
					}
					else v.add(new quad("store",tmp,null,r.loc));
					//--------------------------------------------//
					
				}
				else
				{
					if(t instanceof struct)
					{
						/*location ll = new location(0,"const",0,false,false);
						ll.contain = ((returnrecord)child.get(2).record).rtype.size;
						if(r.loc.address)v.add(new quad("store",((returnrecord)child.get(2).record).loc,ll,r.loc));
						else v.add(new quad("store",((returnrecord)child.get(2).record).loc,ll,r.loc));*/
						
						//-------------------------------------------//
						int k = t.size;
						for(int j=0;j<k/4;j++)
						{
							location t1 = new location(((returnrecord)child.get(2).record).loc.offset+j*4,"memory",0,false,false);
							if(first.func==null)t1.global = true;
							else t1.global = false;
							location t2 = new location(r.loc.offset+j*4,"memory",0,false,false);
							if(first.func==null)t2.global = true;
							else t2.global = false;
							location tmp = new temp(1);
							v.add(new quad("load",tmp,null,t1));
							v.add(new quad("store",tmp,null,t2));
						}
						//-------------------------------------------//
						
						//save struct
					}
					else 
					{
						/*if(((returnrecord)child.get(2).record).rtype.typename.equals("pointer") && t.typename.equals("pointer"))
						{
							location tmp = new temp(1);
							v.add(new quad("load",tmp,null,((returnrecord)child.get(2).record).loc));
							v.add(new quad("store",tmp,null,r.loc));
						}
						else
						{*/
							//-------------------------------------------//
							location tmp = new temp(1);
							location tmp2 = new temp(2);
							v.add(new quad("load",tmp,null,((returnrecord)child.get(2).record).loc));
							if(((returnrecord)child.get(2).record).loc.address && r.loc.address)
							{
								v.add(new quad("lal",tmp,null,tmp));
								v.add(new quad("load",tmp2,null,r.loc));
								v.add(new quad("sal",tmp,null,tmp2));
								
							}
							else if(((returnrecord)child.get(2).record).loc.address)
							{
								v.add(new quad("lal",tmp2,null,tmp));
								v.add(new quad("store",tmp2,null,r.loc));
							}
							else if(r.loc.address)
							{
								v.add(new quad("load",tmp2,null,r.loc));
								v.add(new quad("sal",tmp,null,tmp2));
							}
							else v.add(new quad("store",tmp,null,r.loc));
							
							//-------------------------------------------//
						
						//}
					}
				}
				
			}
				
					
			else
			{
				if (checkconvert(((returnrecord)child.get(2).record).rtype,new Tint())!=0) throw new Exception();

				if(t instanceof pointer && !(((String)child.get(1).record).equals("+=") || ((String)child.get(1).record).equals("-=")))throw new Exception();

				if (checkconvert(t,new Tint())!=0) 
				{
					throw new Exception();
				}
				if(((returnrecord)child.get(2).record).constant)
				{
					if(((returnrecord)son.record).rtype.equals("pointer"))
					{
						location l = new location(0,"const",0,false,false);
						l.contain = (int)((returnrecord)child.get(2).record).value*((pointer)((returnrecord)son.record).rtype).elementType.size;
						location tmp = new temp(1);
						v.add(new quad("load",tmp,null,((returnrecord)son.record).loc));
						v.add(new quad(((String)child.get(1).record),tmp,tmp,l));
						v.add(new quad("store",tmp,null,((returnrecord)son.record).loc));
					}
					else
					{
						location l = new location(0,"const",0,false,false);
						l.contain = ((returnrecord)child.get(2).record).value;
						location tmp = new temp(1);
						v.add(new quad("load",tmp,null,((returnrecord)son.record).loc));
						v.add(new quad(((String)child.get(1).record),tmp,tmp,l));
						v.add(new quad("store",tmp,null,((returnrecord)son.record).loc));
					}
					
				}
				else
				{
					location tmp = new temp(1);
					v.add(new quad("load",tmp,null,r.loc));
					location tmp2 = new temp(2);
					location tmp3 = new temp(3);
					v.add(new quad("load",tmp2,null,((returnrecord)child.get(2).record).loc));
					if(((returnrecord)child.get(2).record).loc.address && r.loc.address)
					{
						v.add(new quad("lal",tmp3,null,tmp));
						v.add(new quad("lal",tmp2,null,tmp2));
						v.add(new quad(((String)child.get(1).record),tmp3,tmp3,tmp2));
						v.add(new quad("sal",tmp3,null,tmp));
						
					}
					else if(((returnrecord)child.get(2).record).loc.address)
					{
						v.add(new quad("lal",tmp3,null,tmp2));
						v.add(new quad(((String)child.get(1).record),tmp,tmp,tmp3));
						v.add(new quad("store",tmp,null,r.loc));
					}
					else if(r.loc.address)
					{
						v.add(new quad("lal",tmp3,null,tmp));
						v.add(new quad(((String)child.get(1).record),tmp3,tmp3,tmp2));
						v.add(new quad("sal",tmp3,null,tmp));
					}
					else
					{
						v.add(new quad(((String)child.get(1).record),tmp,tmp,tmp2));
						v.add(new quad("store",tmp,null,r.loc));
					}
					
				}
				
			}
			record = r;
			
			
		}
		else
		{
			if(child.get(0).check()!=0)
			{
				throw new Exception();
			}
			record = child.get(0).record;
		}
		for(int i=0;i<child.size();i++)code.addAll(child.get(i).code);
		code.addAll(v);
		
		return 0;
	}
}
