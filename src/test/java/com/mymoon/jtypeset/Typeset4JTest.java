package com.mymoon.jtypeset;

public class Typeset4JTest {
	public static void main(String[] args) {
		String s = "ｍｎ            123    abc：      ABC博１２３\nABC与ａｂｃ与ＡＢＣ客园，。是的“吧”！在,9:30";
				
		Typeset4J typeset4J = new Typeset4J();		
		String ss = typeset4J.parse_text(s);
		
		System.out.println(s);
		System.out.println("---------------------------");
		System.out.println(ss);		
	}
}
