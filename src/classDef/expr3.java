package classDef;

public class expr3 extends root
{
	public expr3(){}
	public int check() throws Exception
	{
		for(int i=0;i<child.size();i++)
		{
			if(child.get(i).check()!=0)throw new Exception();
		}
		record = child.get(0).record;
		for(int i=0;i<child.size();i++)code.addAll(child.get(i).code);
		return 0;
	}
}
