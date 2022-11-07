package com.versionsystem.common;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class ProcessBuilderInstance
{
	
	
  
  

  // can run basic ls or ps commands
  // can run command pipelines
  // can run sudo command if you know the password is correct
  public ProcessBuilderInstance() throws IOException, InterruptedException
  {   
  }
  
	public boolean exeCommand(String command) {
		// install awk:sudo apt install mawk
		// convert black.png -format '%[mean] %[max]' info:- | mawk '{print $1/$2}'
		try {
			System.out.println(command);
			List<String> commands = new ArrayList<String>();
			commands.add("/bin/sh");
			commands.add("-c");
			commands.add(command);

			// execute the command
			SystemCommandExecutor commandExecutor = new SystemCommandExecutor(commands);
			int result = commandExecutor.executeCommand();

			// get the stdout and stderr from the command that was run

			StringBuilder stderr = commandExecutor.getStandardErrorFromCommand();
			if (stderr != null && stderr.length() > 0) {
				System.out.println("stderr:");
				System.out.println(stderr);
				return false;
			} else {
				StringBuilder stdout = commandExecutor.getStandardOutputFromCommand();
				if (stdout != null && stdout.length() > 0) {

					System.out.println(stdout);

				}
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

  public boolean pngToPdf(String pngFile, String pdfName) {
		try {

			
			List<String> commands = new ArrayList<String>();
			commands.add("/bin/sh");
			commands.add("-c");
			commands.add("convert " + pngFile  + " " + pdfName);
			System.out.println("convert " + pngFile  + " " + pdfName);
			// execute the command
			SystemCommandExecutor commandExecutor = new SystemCommandExecutor(commands);
			int result = commandExecutor.executeCommand();

			// get the stdout and stderr from the command that was run
			StringBuilder stdout = commandExecutor.getStandardOutputFromCommand();
			System.out.println(stdout);
			StringBuilder stderr = commandExecutor.getStandardErrorFromCommand();
			if (stderr.indexOf("error:") != -1) {
				System.out.println(stderr);
				return false;
			} else
				return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

  
  public boolean pdfToPng(String pdfPath,String pdfName,String pngFolder) {
	  try {
		  	
		
		    List<String> commands = new ArrayList<String>();
		    commands.add("/bin/sh");
		    commands.add("-c");
		    commands.add("convert -density 200 "+pdfPath+" -depth 8 -strip -background white -alpha off -colorspace rgb "+pngFolder+pdfName+"%d.png");
	
		    // execute the command
		    SystemCommandExecutor commandExecutor = new SystemCommandExecutor(commands);
		    int result = commandExecutor.executeCommand();
	
		    // get the stdout and stderr from the command that was run
		    //StringBuilder stdout = commandExecutor.getStandardOutputFromCommand();
		    StringBuilder stderr = commandExecutor.getStandardErrorFromCommand();
		    System.out.println(stderr);
		    return true;
	    
	  }catch(Exception e) {
		  e.printStackTrace();
		  return false;
	  }
  }
  
  public boolean pdfToOcr(String pdfPath, String pdfocrPath) {
		try {
			// multi language ,if ocr text needs to be kept,add the option '--sidecar
			// output.txt'
			// String command ="ocrmypdf -l eng+chi_tra --pdf-renderer tesseract
			// --output-type pdf --sidecar output.txt --tesseract-timeout 300 test.pdf
			// testocr.pdf";

			// single language
			String command = "ocrmypdf --tesseract-timeout 300 " + pdfPath + " " + pdfocrPath;
			System.out.println(command);
			List<String> commands = new ArrayList<String>();
			commands.add("/bin/sh");
			commands.add("-c");
			commands.add(command);

			// execute the command
			SystemCommandExecutor commandExecutor = new SystemCommandExecutor(commands);
			int result = commandExecutor.executeCommand();

			// get the stdout and stderr from the command that was run
			StringBuilder stdout = commandExecutor.getStandardOutputFromCommand();
			System.out.println(stdout);
			StringBuilder stderr = commandExecutor.getStandardErrorFromCommand();
			System.out.println(stderr);
			if (stderr.indexOf("error:") != -1) {
				System.out.println(stderr);
				return false;
			} else
				return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

  
  public boolean pdfToOcr2(String pdfPath) {
	  try {
	    List<String> commands = new ArrayList<String>();
	    commands.add("/bin/sh");
	    commands.add("-c");
	    commands.add("pdfsandwich -lang eng+chi_tra "+pdfPath);

	    // execute the command
	    SystemCommandExecutor commandExecutor = new SystemCommandExecutor(commands);
	    int result = commandExecutor.executeCommand();

	    // get the stdout and stderr from the command that was run
	    //StringBuilder stdout = commandExecutor.getStandardOutputFromCommand();
	    StringBuilder stderr = commandExecutor.getStandardErrorFromCommand();
	    System.out.println(stderr);
	    
	    return true;
	    
	  }catch(Exception e) {
		  e.printStackTrace();
		  return false;
	  }
  }
  
  public boolean isBlankImage(String imageName) {
	  //convert black.png  -format '%[mean] %[max]' info:- | mawk '{print $1/$2}'
	  try {
		    List<String> commands = new ArrayList<String>();
		    commands.add("/bin/sh");
		    commands.add("-c");
		    commands.add("convert "+imageName+"  -format '%[mean] %[max]' info:- | mawk '{print $1/$2}' ");

		    // execute the command
		    SystemCommandExecutor commandExecutor = new SystemCommandExecutor(commands);
		    int result = commandExecutor.executeCommand();
		    
		    // get the stdout and stderr from the command that was run
		    StringBuilder stdout = commandExecutor.getStandardOutputFromCommand();
		    if(stdout!=null && stdout.length()>0) {
		    	double returnvalue=Double.parseDouble(stdout.toString());
		    	if(returnvalue<0.01 ||returnvalue>0.995) {
		    		return true;
		    	}
		    }
		    StringBuilder stderr = commandExecutor.getStandardErrorFromCommand();
		    if(stderr!=null && stderr.length()>0) {
		    	System.out.println("stderr:");
		    	System.out.println(stderr);
		    	
		    }
		    return false;
		    
		  }catch(Exception e) {
			  e.printStackTrace();
			  return false;
		  }
	  
  }
  public boolean dcmCommand(String command) {
	  try {
		System.out.println(command);
	    List<String> commands = new ArrayList<String>();
	    commands.add("/bin/sh");
	    commands.add("-c");
	    commands.add(command);

	    // execute the command
	    SystemCommandExecutor commandExecutor = new SystemCommandExecutor(commands);
	    int result = commandExecutor.executeCommand();

	    // get the stdout and stderr from the command that was run
	    //StringBuilder stdout = commandExecutor.getStandardOutputFromCommand();
	    StringBuilder stderr = commandExecutor.getStandardErrorFromCommand();	    
	    if (stderr.indexOf("error:") != -1 || stderr.indexOf("Exception:") != -1) {
			System.out.println(stderr);
			return false;
		} else
			return true;
	    
	    
	  }catch(Exception e) {
		  e.printStackTrace();
		  return false;
	  }
  }
  
  public boolean hl7snd(String pdfPath) {
	  try {
	    List<String> commands = new ArrayList<String>();
	    commands.add("/bin/sh");
	    commands.add("-c");
	    commands.add("/home/administrator/dicomserver/dcm4che-5.11.0/bin/hl7snd -c 192.168.88.17:2575 "+ 
	    		" "+pdfPath);

	    // execute the command
	    SystemCommandExecutor commandExecutor = new SystemCommandExecutor(commands);
	    int result = commandExecutor.executeCommand();

	    // get the stdout and stderr from the command that was run
	    //StringBuilder stdout = commandExecutor.getStandardOutputFromCommand();
	    StringBuilder stderr = commandExecutor.getStandardErrorFromCommand();
	    System.out.println(stderr);
	    
	    return true;
	    
	  }catch(Exception e) {
		  e.printStackTrace();
		  return false;
	  }
  }
}