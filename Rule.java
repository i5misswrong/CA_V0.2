package CA;

import java.util.Vector;

public class Rule  {
	Vector<Peo> people=new Vector<Peo>();
	public Vector<Peo> getPeople() {
		return people;
	}
	public void setPeople(Vector<Peo> people) {
		this.people = people;
	}
	public void testV(){
		System.out.println("peoµÄ³ß´ç"+people.size());
	}
	
}
