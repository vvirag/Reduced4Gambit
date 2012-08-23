import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import lse.math.games.LogUtils;
import lse.math.games.io.ExtensiveFormXMLReader;
import lse.math.games.reduced.ReducedForm;
import lse.math.games.tree.ExtensiveForm;
import lse.math.games.tree.SequenceForm.ImperfectRecallException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
 

public class Reduced4Gambit {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String help = "USAGE\njava -jar Reduced4Gambit.jar [options] [FILE]";
		help += "\n\nOPTIONS";
		help += "\n-lrs --lrs-path PATH \t#Sets the path to the lrs binary#";
		help += "\n-log --log-level VALUE \t#Sets the log level (VALUE in {0,1,2,3,4} where \n" +
				"\t\t\t  0 MINIMAL\n" +
				"\t\t\t  1 SHORT\n" +
				"\t\t\t  2 DETAILED\n" +
				"\t\t\t  3 DEBUG" +" )#";
		help += "\n-h --help \t\t#Prints this message#";
		help += "\n\nEXAMPLE";
		help += "\nReduced4Gambit$ java -jar bin/Reduced4Gambit.jar -l ./bin/ input/test2.xml";
		
		String lrsDir = ".";
		String input = "";
		Integer loglevel = 0;
		
		int i = 0;
		while (i < args.length) {
		
			if (args[i].equalsIgnoreCase("--help") || args[i].equalsIgnoreCase("-h")) {
				System.out.println(help);
				return;
			}
			
			if (args[i].equalsIgnoreCase("--lrs-path") || args[i].equalsIgnoreCase("-lrs")) {
				if (i+1 >= args.length) {
					System.out.println(help);
					return;
				} else {
					lrsDir = args[i+1];
					i+=2;
					continue;
				}
			}
			
			if (args[i].equalsIgnoreCase("--log-level") || args[i].equalsIgnoreCase("-log")) {
				if (i+1 >= args.length) {
					System.out.println(help);
					return;
				} else {
					loglevel = Integer.parseInt(args[i+1]);
					i+=2;
					continue;
				}
			}
			
			break;
		}
		
		if (i != args.length-1) {
			System.out.println(help);
			return;			
		}
		input = args[i];
		
		LogUtils.currentLogLevel = LogUtils.LogLevel.getFromInteger(loglevel);
		
		System.out.println("================================");
		System.out.println("Log level is: " + loglevel + " (" + LogUtils.currentLogLevel.name() + ")");
		System.out.println("Path to the lrs binary is: " + lrsDir);
		System.out.println("The input file is: " + input);
		System.out.println("================================\n");
		
		File file = new File(input);
		ExtensiveForm tree = null;
		
		try {
			/* Parse file */
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(file);
			
			/* Process with ExtensiveFormXMLReader */
			ExtensiveFormXMLReader reader = new ExtensiveFormXMLReader();
			tree = reader.load(doc);
			
			/* Create a ReducedForm type variable*/
			ReducedForm reducedForm = new ReducedForm(tree);
			
			/* Set the path to the lrs binary */
			reducedForm.setLrsPath(lrsDir);
			
			/* Print the variables of the original system */
			reducedForm.printOriginalSystem();
			
			/* Print the variables of the reduced system */
			reducedForm.printReducedSystem();
			
			/* Find all equilibria based on reduced system + lrs search */
			reducedForm.findEqLrs();
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ImperfectRecallException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// This method writes a DOM document to a file
	public static void writeXmlFile(Document doc, String filename) {
	    try {
	        // Prepare the DOM document for writing
	        Source source = new DOMSource(doc);

	        // Prepare the output file
	        File file = new File(filename);
	        Result result = new StreamResult(file);

	        // Write the DOM document to the file
	        Transformer xformer = TransformerFactory.newInstance().newTransformer();
	        xformer.transform(source, result);
	    } catch (TransformerConfigurationException e) {
	    } catch (TransformerException e) {
	    }
	}
	
	static Node getExtensiveForm(Node node) {
		if ("extensiveForm".equals(node.getNodeName())) {
			return node;
		}
		
		if (node.getFirstChild() != null) {
			return getExtensiveForm(node.getFirstChild());
		}
		
		while (node.getNextSibling() != null) {
			node = node.getNextSibling();
			if ("extensiveForm".equals(node.getNodeName())) {
				return node;
			}
		}
				
		return null;
	}
}
