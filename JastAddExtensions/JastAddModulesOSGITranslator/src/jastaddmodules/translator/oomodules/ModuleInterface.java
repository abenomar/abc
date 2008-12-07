package jastaddmodules.translator.oomodules;

import java.util.Collection;
import java.util.LinkedList;

public class ModuleInterface extends AbstractModule {
	
	public ModuleInterface(String name) {
		super(name);
	}
	
	public ModuleInterface(String name, 
			ModuleInterface superModule, 
			Collection<String> exportedPackages) {
		super(name);
		this.superModule = superModule;
		if (exportedPackages != null) {
			this.exportedPackages = new LinkedList<String>(exportedPackages);
		}
	}
	
	@Override
	protected String getModuleKeyword() {
		return "module_interface";
	}
}
