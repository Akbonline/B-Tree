import java.io.*;
import java.util.*;

import javax.swing.plaf.basic.BasicTreeUI.TreeIncrementAction;
public class generate {         //Root Class to implement rest of the tree
	public static void main(String[] args) throws Exception {   
		Map<String,String> input=new HashMap<String,String>(); //Buffer to keep track of evry element inserted
		
		
		String range="";    //stores the return value from range search function    
		String find="";     //stores the return value from single key search function
		File location = new File(args[0]+".txt");      //Input file Location
		BufferedReader inputfile = new BufferedReader(new FileReader(location));    //Read Buffer to extract the inputs
		
		File outputFile = new File("output_file.txt");                  //Output file location
		if(!outputFile.exists()){
			outputFile.createNewFile();                                 //Creating new output file
		}
		FileWriter filewriter = new FileWriter(outputFile.getAbsoluteFile());   //Writer object init
		PrintWriter outputfile = new PrintWriter(filewriter);
		
		
	
		
		String lineRead=inputfile.readLine();
		if(lineRead.startsWith("Initialize")){
            int order=Integer.parseInt((lineRead.split("Initialize\\(")[1].split("\\)")[0]).trim());
            Tree B_Plus = new Tree(order);
        
		while((lineRead = inputfile.readLine()) != null){	
            
			if(lineRead.startsWith("Insert")){              //If the Input Reader Line starts with "Insert"
				String key=lineRead.split("Insert\\(")[1].split(",")[0];        //Get the key 
				String value=lineRead.split(",")[1].split("\\)")[0];        //Get the value
				input.put(lineRead.split("Insert\\(")[1].split(",")[0],lineRead.split(",")[1].split("\\)")[0]);     //inserting the key and value 
				B_Plus.push(Double.parseDouble(key),value);         //recording the inserted files
				
			}
			else if(lineRead.startsWith("Search")){             //If the line read starts with "Search" 
				if(lineRead.contains(",")){                     //This shows that there is a range passed through this argument
					
					range = B_Plus.find(Double.parseDouble(lineRead.split("Search\\(")[1].split(",")[0]),Double.parseDouble(lineRead.split("Search\\(")[1].split(",")[1].split("\\)")[0])).trim();
					if( range.charAt(range.length() - 1) == ','){
						range = range.substring(0, range.length() - 1);
					}
					outputfile.println(range);
					
				}
				else{           //If not, then the Search command is of single key search type
					find = B_Plus.find(Double.parseDouble(lineRead.split("Search\\(")[1].split("\\)")[0])).trim();
					if( find.charAt(find.length() - 1) == ','){
						find = find.substring(0, find.length() - 1);
					}
					outputfile.println(find);
					
				}
            }
            else if(lineRead.startsWith("Delete")){         //If the Line Starts with Delete keyword        
                String key_to_delete=(lineRead.split("Delete\\(")[1].split("\\)")[0]).trim();       //fetching the key
                B_Plus=B_Plus.delete(input,Integer.parseInt(key_to_delete));            //delete function called with key passed as the argument 
                
            }
            else{
                outputfile.println("Please check you input");   //If the input file has any faulty input 
            }
		}		
        }
        else{
            outputfile.println("Please Ensure that you start the input with Initialize(order)");        //If the input not initialized first
        }
        filewriter.close();             //closing the output buffer
		inputfile.close();              //closing the input buffer

	}
	

}

    

   

   
 

     


