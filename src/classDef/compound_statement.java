package classDef;

import core.first;

public class compound_statement extends root
{
	public compound_statement(){}
	public int check() throws Exception
	{
		first.beginscope(0);
		for(int i=0;i<child.size();i++)
		{
			if(child.get(i).check()!=0)
			{
				throw new Exception();
				//return 1;
			}
		}
		first.endscope(0);
		return 0;
		
	}
}
