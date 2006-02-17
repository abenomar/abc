import AST.*;

import java.util.*;
import java.io.*;
import parser.*;

public class JastAdd {
  public static void main(String args[]) {
    if(!compile(args))
      System.exit(1);
  }

  public static boolean compile(String args[]) {
    Program program = new Program();
    program.initOptions();
    program.addKeyValueOption("-classpath");
    program.addKeyValueOption("-sourcepath");
    program.addKeyValueOption("-bootclasspath");
    program.addKeyValueOption("-extdirs");
    program.addKeyValueOption("-d");
    program.addKeyOption("-verbose");
    program.addKeyOption("-version");
    program.addKeyOption("-help");

    program.addKeyOption("-no_cache_cycle");
    program.addKeyOption("-no_visit_check");
    
    program.addOptions(args);
    Collection files = program.files();
    
    if(program.hasOption("-version")) {
      printVersion();
      return false;
    }
    if(program.hasOption("-help") || files.isEmpty()) {
      printUsage();
      return false;
    }

    JavaParser parser = new JavaParser();
    for(Iterator iter = files.iterator(); iter.hasNext(); ) {
      String name = (String)iter.next();
      try {
        Reader reader = new FileReader(name);
        JavaScanner scanner = new JavaScanner(new UnicodeEscapes(new BufferedReader(reader)));
        CompilationUnit unit = ((Program)parser.parse(scanner)).getCompilationUnit(0);
        unit.setFromSource(true);
        unit.setRelativeName(name);
        unit.setPathName(".");
      	reader.close();
        program.addCompilationUnit(unit);
      } catch (Error e) {
        System.err.println(name + ": " + e.getMessage());
        return false;
      } catch (RuntimeException e) {
        System.err.println(name + ": " + e.getMessage());
        return false;
      } catch (IOException e) {
        System.err.println("error: " + e.getMessage());
        return false;
      } catch (Exception e) {
        System.err.println(e);
        e.printStackTrace();
      }
    }
    program.updateRemoteAttributeCollections(files.size());
    if(Program.verbose())
        program.prettyPrint(files.size());
    if(program.errorCheck(files.size())) {
      if(Program.verbose())
        program.prettyPrint(files.size());
    }
    else {
      program.generateIntertypeDecls(files.size());
      if(Program.verbose())
        program.prettyPrint(files.size());
      program.updateRemoteAttributeCollections(files.size());
      program.generateClassfile(files.size());
      return true;
    }
    return false;
  }
  
  protected static void printUsage() {
    printVersion();
    System.out.println(
      "\nJastAdd\n\n" +
      "Usage: java JastAdd <options> <source files>\n" +
      "  -verbose                  Output messages about what the compiler is doing\n" +
      "  -classpath <path>         Specify where to find user class files\n" +
      "  -sourcepath <path>        Specify where to find input source files\n" + 
      "  -bootclasspath <path>     Override location of bootstrap class files\n" + 
      "  -extdirs <dirs>           Override location of installed extensions\n" +
      "  -d <directory>            Specify where to place generated class files\n" +
      "  -help                     Print a synopsis of standard options\n" +
      "  -version                  Print version information\n" +
      "  -no_cache_cycle           Disable cache cycle optimization for circular attributes\n" +
      "  -no_visit_check           Disable visit checks used to detect circularities for attributes\n"
    );
  }

  protected static void printVersion() {
    System.out.println("JastAdd (Experimental Bootstrapped Version) (http://jastadd.cs.lth.se) Version R20060217");
  }
}
