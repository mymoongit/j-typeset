package com.mymoon.jtypeset;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Typeset4J {
	/**
	 * 函数
	 */
	public String str_escape(String s) {
		s = s.replace("\\\\", "\\");
		s = s.replace("\\\'", "\'");
		s = s.replace("\\\"", "\"");
		s = s.replace("\\n", "\n");
		s = s.replace("\\r", "\r");
		s = s.replace("\\t", "\t");
		s = s.replace("\\0", "");
		
		return s;
	}	
	
	// 中文
	public boolean zh_letter(char c) {		
		return (c >= '\u4e00' &&  c <= '\u9fa5');
	}	
	public boolean zh_l_punc(char c) {		
		return ("（【《￥".contains(c+""));
	}
	public boolean zh_r_punc(char c) {		
		return ("，。？！：；）】》".contains(c+""));
	}
	public boolean zh_m_punc(char c) {		
		return ("·～—".contains(c+""));
	}
	public boolean zh_quote(char c) {		
		return ("“‘「『”’」』".contains(c+""));
	}
	public boolean zh_punc(char c) {		
		return (zh_l_punc(c) || zh_r_punc(c) || zh_m_punc(c));
	}
	public boolean zh_char(char c) {		
		return (zh_letter(c) || zh_punc(c) || zh_quote(c));
	}
	
	// 英文
	public boolean en_letter(char c) {		
		return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
	}	
	public boolean en_l_punc(char c) {		
		return ("([{@#$".contains(c+""));
	}
	public boolean en_r_punc(char c) {		
		return (",.?!:;)]}%".contains(c+""));
	}
	public boolean en_r_punc_digit(char c) {		
		return ("?!;)]}%".contains(c+""));
	}
	public boolean en_m_punc(char c) {		
		return ("+-*/\\=<>_^&|~".contains(c+""));
	}
	public boolean en_quote(char c) {		
		return ("\'\"`".contains(c+""));
	}	
	public boolean en_punc(char c) {		
		return (en_m_punc(c) || en_l_punc(c) || en_r_punc(c));
	}	
	public boolean en_char(char c) {		
		return (en_letter(c) || en_punc(c) || en_quote(c));
	}
	
	// 中英文
	public boolean letter(char c) {		
		return (zh_letter(c) || en_letter(c));
	}
	public boolean punc(char c) {		
		return (zh_punc(c) || en_punc(c));
	}
	public boolean digit(char c) {		
		return (c >= '0' && c <= '9');
	}

	// 全角转半角
	public String  correct_full_width(String s) {				
		String[] full_list = {
				"０", "１", "２", "３", "４", "５", "６", "７", "８", "９", "Ａ", "Ｂ",
                "Ｃ", "Ｄ", "Ｅ", "Ｆ", "Ｇ", "Ｈ", "Ｉ", "Ｊ", "Ｋ", "Ｌ", "Ｍ", "Ｎ",
                "Ｏ", "Ｐ", "Ｑ", "Ｒ", "Ｓ", "Ｔ", "Ｕ", "Ｖ", "Ｗ", "Ｘ", "Ｙ", "Ｚ",
                "ａ", "ｂ", "ｃ", "ｄ", "ｅ", "ｆ", "ｇ", "ｈ", "ｉ", "ｊ", "ｋ", "ｌ",
                "ｍ", "ｎ", "ｏ", "ｐ", "ｑ", "ｒ", "ｓ", "ｔ", "ｕ", "ｖ", "ｗ", "ｘ",
                "ｙ", "ｚ", "－", "／", "．", "％", "＃", "＠", "＆", "＜", "＞", "［",
                "］", "｛", "｝", "＼", "｜", "＋", "＝", "＿", "＾", "｀", "‘‘", "’’"
					};
		
		String[] half_list = {
				"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B",
                "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
                "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
                "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x",
                "y", "z", "-", "/", ". ", "%", "#", "@", "&", "<", ">", "[",
                "]", "{", "}", "\\", "|", "+", "=", "_", "^", "`", "“", "”"
					}; 		
		
		for(int i = 0; i < full_list.length; i++) {			
			s = s.replace(full_list[i], half_list[i]);
		}		

		return s;
	}
		
	static String[][] remove_space_type = {
											{ "zh_char", "zh_char" }, 
											{ "zh_char", "digit" }, 
											{ "digit", "zh_char" }, 
											{ "zh_letter", "en_letter" },
											{ "en_letter", "zh_letter" }, 
											{ "zh_letter", "en_r_punc" }, 
											{ "en_l_punc", "zh_letter" },
											{ "zh_punc", "en_char" }, 
											{ "en_char", "zh_punc" }, 
											{ "en_letter", "en_r_punc" },
											{ "en_l_punc", "en_letter" }, 
											{ "en_l_punc", "en_l_punc" }, 
											{ "en_l_punc", "en_r_punc" },
											{ "en_l_punc", "en_m_punc" }, 
											{ "en_r_punc", "en_r_punc" }, 
											{ "en_m_punc", "en_r_punc" },
											{ "en_m_punc", "en_m_punc" }, 
											{ "digit", "en_r_punc" }, 
											{ "en_l_punc", "digit" } };	
	
	static String[][] add_space_type = {
											{ "zh_letter", "en_l_punc" },
											{ "zh_letter", "en_m_punc" },
											{ "en_r_punc", "zh_letter" },
											{ "en_m_punc", "zh_letter" },
											{ "en_letter", "en_l_punc" },
											{ "en_letter", "en_m_punc" }, 
											{ "en_r_punc", "en_letter" },
											{ "en_m_punc", "en_letter" },
											{ "en_r_punc", "en_l_punc" }, 
											{ "en_r_punc", "en_m_punc" },
											{ "en_m_punc", "en_l_punc" },
											{ "digit", "en_l_punc" }, 
											{ "digit", "en_m_punc" }, 
											{ "en_r_punc_digit", "digit" },	
											{ "en_m_punc", "digit" } };
	
	static Class<?> cls = null;
	static Object obj = null;
	static {		
		try {
			cls = Class.forName("com.mymoon.jtypeset.Typeset4J");
			obj = cls.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String correct_space(String s) {	
		List<String> charList = new ArrayList<>();
	    for (int i = 0; i < s.toCharArray().length; i++) {
	    	charList.add(s.toCharArray()[i] + "");
	    }	
		
		try {
								
			for(int i = 0; i < charList.size() - 1; i++) {
				if (charList.get(i).equals(" ")) {
					for(int j = 0; j < remove_space_type.length; j++) {
						String type_l = remove_space_type[j][0];
						String type_r = remove_space_type[j][1];						
						Method m_l = cls.getMethod(type_l, char.class);
						Method m_r = cls.getMethod(type_r, char.class);
						
						int index_1 = i - 1;
						if(i == 0) {
							index_1 = charList.size() - 1;
						}
											
						if((boolean)m_l.invoke(obj, charList.get(index_1).charAt(0)) && (boolean)m_r.invoke(obj, charList.get(i+1).charAt(0))) {							
							charList.set(i, "");
							break;
						}
					}					
				}else {					
					for(int j = 0; j < add_space_type.length; j++) {
						String type_l = add_space_type[j][0];
						String type_r = add_space_type[j][1];						
						Method m_l = cls.getMethod(type_l, char.class);
						Method m_r = cls.getMethod(type_r, char.class);
									
						if((boolean)m_l.invoke(obj, charList.get(i).charAt(0)) && (boolean)m_r.invoke(obj, charList.get(i+1).charAt(0))) {
							charList.set(i, charList.get(i) + ' ');
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		s = String.join("", charList).trim();
		
		return s;
	}
	
	public static Map<String, String> argsMap = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			this.put("comment_mark", "");				
			this.put("in_encoding", "");
			this.put("in_filename", "");			
			this.put("minor_space", "");
			this.put("normalize_unicode", "");					
			this.put("out_filename", "");			
			this.put("zh_period", "empty");
			this.put("zh_quote", "curly");
			this.put("out_encoding", "utf-8");			
			this.put("eol", "\n");				
			this.put("max_eol", "2");
			this.put("guess_lang_window", "4");
			this.put("tex_quote", "false");
		}		
	};	
	static String[][] minor_space_type = {
			{ "zh_letter", "en_letter" },
			{ "en_letter", "zh_letter" },
			{ "zh_letter", "digit" },
			{ "digit", "zh_letter" }};
	public String correct_minor_space(String s) {		
		String minor_space = argsMap.get("minor_space");	
		List<String> charList = new ArrayList<>();
	    for (int i = 0; i < s.toCharArray().length; i++) {
	    	charList.add(s.toCharArray()[i] + "");
	    }	
	    
		try {			
			for(int i = 0; i < charList.size() - 1; i++) {				
				for(int j = 0; j < minor_space_type.length; j++) {						
					String type_l = minor_space_type[j][0];
					String type_r = minor_space_type[j][1];						
					Method m_l = cls.getMethod(type_l, char.class);
					Method m_r = cls.getMethod(type_r, char.class);
					
					if((boolean)m_l.invoke(obj, charList.get(i).charAt(0)) && (boolean)m_r.invoke(obj, charList.get(i+1).charAt(0))) {
						charList.set(i, charList.get(i) + minor_space);
						break;
					}				
				}				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		s = String.join("", charList).trim();	
				
	    return s;
	}
	
	public String guess_lang(String s) {
	    int i = 0;
	    int j = s.length() - 1;
	    
	    char c_i = s.charAt(i);
	    while (i < j && (!zh_letter(c_i) && !en_letter(c_i))) {
	        i += 1;	        
	        c_i = s.charAt(i);        
	    }
	        
	    char c_j = s.charAt(j);
	    while (i < j && !zh_letter(c_j) && !en_letter(c_j)) {
	    	 j -= 1;
	    	 c_j = s.charAt(j);
	    }	
	    	    
	    if(i >= j) {
	        return "zh";
	    }
	    
	    int zh_count = 0;
	    int en_count = 0;
	    	    
	    int guess_lang_window = Integer.parseInt(argsMap.get("guess_lang_window"));	    	    	    
	    List<Integer> rangList = new ArrayList<>();
	    for(int k = i; k < Math.min((i + guess_lang_window), j); k++) {
	    	rangList.add(k);
	    }	    
	    for(int k = Math.max((j - guess_lang_window), i); k < j ; k++) {
	    	rangList.add(k);
	    }
	    
	    for(int k : rangList) {
	        if (zh_letter(s.charAt(k))) {
	            zh_count += 1;
	        }else if(en_letter(s.charAt(k))) {
	            en_count += 1;
	        }
	    }	            
	    if (zh_count * 2 >= en_count) {
	        return "zh";
	    }else {
	        return "en";
	    }
	}
	
	public boolean detect_forward(String f, String s, int i){
		try {			
			Method m_f = cls.getMethod(f, char.class);
			
			if(i == 0) {
			    return false;
			}			
			if((boolean)m_f.invoke(obj, s.charAt(i - 1)) == true) {
			    return true;
			}			
			if (s.charAt(i - 1)+"".trim().length() == 1){
			    return false;
			}
			if (i == 1) {
			    return false;
			}
			if((boolean)m_f.invoke(obj, s.charAt(i - 2)) == true) {	    
			    return true;
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return false;
	}	
	public boolean detect_backward(String f, String s, int i){
		try {
			Method m_f = cls.getMethod(f, char.class);
			
			if(i == 0) {
			    return false;
			}			
			if((boolean)m_f.invoke(obj, s.charAt(i + 1)) == true) {
			    return true;
			}			
			if (s.charAt(i + 1)+"".trim().length() == 1){
			    return false;
			}
			if (i == s.length() - 2) {
			    return false;
			}
			if((boolean)m_f.invoke(obj, s.charAt(i + 2)) == true) {	    
			    return true;
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return false;
	}
	
	
	public String correct_punc_zh(String s) {
	    String zh_end_punc_list = "，。？！：；";
	    String en_end_punc_list = ",.?!:;";

		char[] sChars = s.toCharArray();
		for (int i = 0; i < sChars.length; i++) {
			boolean breakFor = false;
			for (int j = 0; j < zh_end_punc_list.length(); j++) {
				char zh_end_punc = zh_end_punc_list.charAt(j);
				char en_end_punc = en_end_punc_list.charAt(j);
				char sChar = sChars[i];
				if (sChar == en_end_punc && detect_forward("zh_char", s, i)) {
					sChars[i] = zh_end_punc;
					breakFor = true;
					break;
				}
			}

			if (breakFor == false) { // 上个循环未中断
				// 根据括号外部的环境修正括号
				if (sChars[i] == '(' && detect_forward("zh_char", s, i))
					sChars[i] = '（';
				else if (sChars[i] == ')' && detect_backward("zh_char", s, i))
					sChars[i] = '）';

				// 根据左括号修正右括号
				if (sChars[i] == '（') {
					int j = i + 1;
					int bracket_count = 0;
					boolean ok = false;
					while (j < s.length()) {
						if (")）".contains(sChars[j] + "")) {
							if (bracket_count == 0) {
								ok = true;
								break;
							} else {
								bracket_count -= 1;
							}
						} else if ("(（".contains(sChars[j] + "")) {
							bracket_count += 1;
						}
						j += 1;
						if (ok && sChars[j] == ')') {
							sChars[j] = '）';
						}
					}
				}
				// 根据右括号修正左括号
				/**
				 *  j -= 1;
				 */
				if (sChars[i] == '）') {
					int j = i - 1;
					int bracket_count = 0;
					boolean ok = false;
					
					while (j >= 0) {						
						if ("(（".contains(sChars[j] + "")) {
							if (bracket_count == 0) {
								ok = true;
								break;
							} else {
								bracket_count -= 1;
							}
						} else if (")）".contains(sChars[j] + "")) {
							bracket_count += 1;
						}
						j -= 1;
						if (ok && sChars[j] == '(') {
							sChars[j] = '（';
						}
					}
				}
			}
	    }
		s = new String(sChars).trim();
	    return s;
	}
	
	public String correct_punc_en(String s) {	
	    String zh_end_punc_list = "，。？！：；";
	    String en_end_punc_list = ",.?!:;";

	    char[] sChars = s.toCharArray();
	    
	    for (int i = 0; i < sChars.length; i++) {	    	
	    	boolean breakFor = false;
			for (int j = 0; j < zh_end_punc_list.length(); j++) {
				char zh_end_punc = zh_end_punc_list.charAt(j);
				char en_end_punc = en_end_punc_list.charAt(j);
				char sChar = sChars[i];
				if (sChar == zh_end_punc && detect_forward("en_char", s, i)) {
					sChars[i] = en_end_punc;
					breakFor = true;
					break;
				}
			}

			if (breakFor == false) { // 上个循环未中断
	            // 根据括号外部的环境修正括号				
				if (sChars[i] == '(' && detect_forward("en_char", s, i))
					sChars[i] = '（';
				else if (sChars[i] == ')' && detect_backward("en_char", s, i))
					sChars[i] = '）';   
	                
	            // 根据左括号修正右括号
				if (sChars[i] == '（') {
					int j = i + 1;
					int bracket_count = 0;
					boolean ok = false;
					while (j < s.length()) {
						if (")）".contains(sChars[j] + "")) {
							if (bracket_count == 0) {
								ok = true;
								break;
							} else {
								bracket_count -= 1;
							}
						} else if ("(（".contains(sChars[j] + "")) {
							bracket_count += 1;
						}
						j += 1;
						if (ok && sChars[j] == ')') {
							sChars[j] = '）';
						}
					}
				}	

	            // 根据右括号修正左括号
				if (sChars[i] == '）') {
					int j = i - 1;
					int bracket_count = 0;
					boolean ok = false;					
					while (j >= 0) {
						if ("(（".contains(sChars[j] + "")) {
							if (bracket_count == 0) {
								ok = true;
								break;
							} else {
								bracket_count -= 1;
							}
						} else if (")）".contains(sChars[j] + "")) {
							bracket_count += 1;
						}
						j -= 1;
						if (ok && sChars[j] == '(') {
							sChars[j] = '（';
						}
					}
				}	
			}
	    }

		s = new String(sChars).trim();
	    return s;
	}
	
	public String correct_quote_zh(String s) {	
		List<String> charList = new ArrayList<>();
	    for (int i = 0; i < s.toCharArray().length; i++) {
	    	charList.add(s.toCharArray()[i] + "");
	    }
	    int quote_state = 0;
	    int quote_state_2 = 0;
	    for (int i = 0; i < charList.size(); i++) {
	    	if("\"“”".contains(charList.get(i))) {
	    		if(quote_state == 0) {
	    			charList.set(i, "“");	    			
	    			quote_state = 1;
	    		}else {	    		
	    			charList.set(i, "”");
	    			quote_state = 0;
	    		}
	    		String last_i = charList.get(i - 1);	 
	    		if(i > 0 && last_i.equals(" ")) {	    			
	    			charList.set(i - 1, "");
	    		}	    		
	    		if(i < charList.size() -  1 && charList.get(i + 1).equals(" ")) {
	    			charList.set(i + 1, "");
	    		}
	    	}else if("‘’".contains(charList.get(i))) {
	    		if(quote_state_2 == 0) {	    			
	    			charList.set(i, "‘");
	    			quote_state_2 = 1;
	    		}else {
	    			charList.set(i, "’");	    			
	    			quote_state_2 = 0;
	    		}
	    		
	    		if( i > 0 && charList.get(i - 1).equals(" ")) {
	    			charList.set(i - 1, "");
	    			
	    		}
	    		if(i < charList.size() -  1 && charList.get(i + 1).equals(" ")) {	    			
	    			charList.set(i + 1, "");
	    		}
	    	}
		}
		s = String.join("", charList).trim();
	    return s;		    
	}
	
	public String correct_quote_en(String s) {		
		List<String> charList = new ArrayList<>();
	    for (int i = 0; i < s.toCharArray().length; i++) {
	    	charList.add(s.toCharArray()[i] + "");
	    }
	    int quote_state = 0;
	    for (int i = 0; i < charList.size(); i++) {	    	
	    	if("\"“”".contains(charList.get(i))) {
	    		if(quote_state == 0) {
	    			if(argsMap.get("tex_quote").equals("true")) {	
	    				charList.set(i, "``");	    				
	    			}else {	    			
	    				charList.set(i, "\"");	
	    			}
	    				    	    			
	    			String last_i = charList.get(i - 1);	    			
	    			char i_end = last_i.charAt(last_i.length() - 1);	    			
	    			if(i > 0 && last_i.length() > 0 && i_end != ' ') {
	    				charList.set(i, ' ' + charList.get(i) + "");
	    			}
	    				    			
	    			String next_i = charList.get(i + 1);
	    			if(i < charList.size() - 1 && next_i.equals(' ' + "")) {	    				
	    				charList.set(i + 1, "");
	    			}	    			
	    			quote_state = 1;
	    		}else {
	    			if(argsMap.get("tex_quote").equals("true")) {
	    				charList.set(i, "''");
	    			}else {
	    				charList.set(i, "\"");
	    			}
	  
	    			String last_i = charList.get(i - 1);	    			
	    			char i_end = last_i.charAt(last_i.length() - 1);	    			
	    			if(i > 0 && last_i.length() > 0 && i_end != ' ') {	    				
	    				StringBuffer i_sb = new StringBuffer(last_i);
	    				i_sb.deleteCharAt(last_i.length() - 1);	    				
	    				charList.set(i - 1, i_sb.toString());
	    			}
	    			String next_i = charList.get(i + 1);
	    			if(i < charList.size() - 1 && !next_i.equals(' ' + "")) {	    
	    				charList.set(i, charList.get(i) + ' ');
	    			}	    					
	    			quote_state = 0;
	    		}    		
	    	}else if(charList.get(i).equals("‘")) {	// elif s[i] == '‘':
	    		if(argsMap.get("tex_quote").equals("true")) {
    				charList.set(i, " `");
    			}else {
    				charList.set(i, " '");
    			}
	    	}else if(charList.get(i).equals("’")) {
	    		charList.set(i, "' ");
	    	}	    	
		}
		
	    s = String.join("", charList).trim();	    
	    return s;		    
	}
	
	private String correct_ellipsis(String s, String ellipsis) {
		int ellipsis_count = 0;
		String ellipsis_list = ".。·…";
		List<String> charList = new ArrayList<>();
	    for (int i = 0; i < s.toCharArray().length; i++) {
	    	charList.add(s.toCharArray()[i] + "");
	    }
		for(int i = 0; i < charList.size(); i++) {
			if(ellipsis_list.contains(charList.get(i)+"")) {
				if (charList.get(i).equals("…")) {
					ellipsis_count = 3;
				}else {
					ellipsis_count = 1;
				}
				int j = i + 1;
				while(j < charList.size()) {
					 if(ellipsis_list.contains(charList.get(j))) {					 
						 if (charList.get(j).equals("…")) {
							 ellipsis_count += 3;
						 }else {
							 ellipsis_count += 1;
						 }
					 }else {
						 break;
					 }
					 j += 1;
				}
				if(ellipsis_count >= 3) {
					charList.set(i, ellipsis);
					for(int k = i + 1; k < j; k++) {
						charList.set(i, "");
					}
				}
			}
		}
		s = String.join("", charList).trim();
		return s;
	}
	
	public String correct_zh_period(String s) {
		if(argsMap.get("zh_period").equals("empty")) {
			s = s.replace('．', '。');
		}else if(argsMap.get("zh_period").equals("dot")) {
			s = s.replace('。', '．');
		}else if(argsMap.get("zh_period").equals("den_dot")) {
			s = s.replace("。", ". ");
			s = s.replace("．", ". ");
		}
		
		return s;
	}
	
	public String correct_zh_quote(String s) {
		if(argsMap.get("zh_quote").equals("curly")) {
			s = s.replace('「', '“');
	        s = s.replace('」', '”');
	        s = s.replace('『', '‘');
	        s = s.replace('』', '’');
		}else if(argsMap.get("zh_quote").equals("rect")){
			s = s.replace('“', '「');
	        s = s.replace('”', '」');
	        s = s.replace('‘', '『');
	        s = s.replace('’', '』');
		}else if(argsMap.get("zh_quote").equals("straight")){
			s = s.replace("“", " \"");
	        s = s.replace("”", "\" ");
	        s = s.replace("‘", " '");
	        s = s.replace("’", "' ");
	        s = s.replace("「", " \"");
	        s = s.replace("」", "\" ");
	        s = s.replace("『", " '");
	        s = s.replace("』", "' ");
		}else if(argsMap.get("zh_quote").equals("tex")){
	        s = s.replace("“", " ``");
            s = s.replace("”", "'' ");
            s = s.replace("‘", " `");
            s = s.replace("’", "' ");
            s = s.replace("「", " ``");
            s = s.replace("」", "'' ");
            s = s.replace("『", " `");
            s = s.replace("』", "' ");
		}
		
		return s;
	}
	
	
	public String parse_text(String s) {
		if(s == null || s.length() == 0) {
			return "";
		}
		
		String comment_mark = argsMap.get("comment_mark");
		String eol = argsMap.get("eol");		
		int max_eol = Integer.parseInt(argsMap.get("max_eol"));
		
		String res = "";
		for(String line : s.split("\r\n|\r|\n")) {				
			String[] ss = line.trim().split("[\\s \t]{1,}");			 
			String res_line = String.join(" ", Arrays.asList(ss));
			if(res_line.length() == 0) {
				res += eol;
				continue;
			}
			if(comment_mark != null && comment_mark.length() > 0 && res_line.startsWith(comment_mark)) {
				res += (line + eol);
				continue;
			}
			
			// java 不需要字段标准化
			
			res_line = correct_full_width(res_line);
			String lang = guess_lang(res_line);
						
			if (lang.equals("zh")) {
	            while(true) {
	                String last_res_line = res_line;
	                res_line = correct_space(res_line);
	                res_line = correct_punc_zh(res_line);
	                res_line = correct_quote_zh(res_line);
	                res_line = correct_ellipsis(res_line, "……");
	                if(res_line.equals(last_res_line)) {
	                    break;
	                }
	            }
			}else{
	            while(true) {
	                String last_res_line = res_line;
	                res_line = correct_space(res_line);
	                res_line = correct_punc_en(res_line);
	                res_line = correct_quote_en(res_line);
	                res_line = correct_ellipsis(res_line, "...");
	                if(res_line.equals(last_res_line)){
	                    break;
	                }
	            }
			}

	        res_line = correct_minor_space(res_line);	        
	        res_line = correct_zh_period(res_line);
	        res_line = correct_zh_quote(res_line);

	        res += res_line + eol;
		}
		
		String reg = "";
		String maxEol = "";
		for(int i = 0; i < max_eol; i++) {
			maxEol += eol;
		}
		reg += maxEol;
		reg += eol;
		reg += '+';
				
		res = res.replaceAll(reg, maxEol);
		
		return res;
	}
}
