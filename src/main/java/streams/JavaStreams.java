package streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.testng.Assert;
import org.testng.annotations.Test;



public class JavaStreams {
	// @Test
	public void regular() {
		ArrayList<String> names = new ArrayList<String>();
		names.add("Aarav");
		names.add("Ilakia");
		names.add("Akhil");
		names.add("Arthi");
		names.add("Dileep");
		names.add("Ram");

		int count = 0;

		for (int i = 0; i < names.size(); i++) {
			String actual = names.get(i);
			if (actual.startsWith("A")) {
				count++;

			}

		}
		System.out.println(count);
	}

//Test
	public void streamFilter() {
		ArrayList<String> names = new ArrayList<String>();
		names.add("Aarav");
		names.add("Ilakia");
		names.add("Akhil");
		names.add("Arthi");
		names.add("Dileep");
		Long c = names.stream().filter(s -> s.startsWith("A")).count();
		System.out.println(c);
		//mes.stream().filter(s -> s.length() > 4).forEach(s -> System.out.println(s));
		names.stream().filter(s -> s.length() > 4).limit(2).forEach(s -> System.out.println(s));

	}
	@Test
	public void streamMap() 
	{
		ArrayList<String> names = new ArrayList<String>();
	names.add("Aravindh");
	names.add("Shobana");
	names.add("Pazham");
	names.add("Akshaya");
	names.add("Diwakar");
		//Stream.of("Aarav","Ilakia","Akhil","Arthi","Dileep","Rama").filter(s->s.endsWith("a")).map(s->s.toUpperCase()).forEach(s->System.out.println(s));
		List<String> names1=Arrays.asList("Azarav","Ilakia","Akhil","Arthi","Dileep","Rama");
		names1.stream().filter(s->s.startsWith("A")).sorted().map(s->s.toUpperCase()).forEach(s->System.out.println(s));
		
		Stream<String> newStream=Stream.concat(names.stream(), names1.stream());
		//newStream.sorted().forEach(s->System.out.println(s));
		boolean flag=newStream.anyMatch(s->s.equalsIgnoreCase("Arthi"));
		Assert.assertTrue(flag);
		System.out.println(flag);
		
		
		
	}
}