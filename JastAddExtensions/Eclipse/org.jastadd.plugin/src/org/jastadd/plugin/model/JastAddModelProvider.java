package org.jastadd.plugin.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.text.IDocument;
import org.jastadd.plugin.AST.IJastAddNode;

public class JastAddModelProvider {
	
	private static LinkedList<JastAddModel> modelList = new LinkedList<JastAddModel>();
	private static HashMap<IFile,JastAddModel> fileModelMap = new HashMap<IFile,JastAddModel>();	
	
	public static List<JastAddModel> getModels() {
		return Collections.unmodifiableList(modelList);
	}
	
	public static List<JastAddModel> getModels(IProject project) {
		List<JastAddModel> list = new ArrayList<JastAddModel>();
		for (Iterator itr = modelList.iterator(); itr.hasNext();) {
			JastAddModel model = (JastAddModel)itr.next();
			if (model.isModelFor(project)) {
				list.add(model);
			}
		}
		return list;
	}
	
	public static JastAddModel getModel(IFile file) {
		if (fileModelMap.containsKey(file)) {
			return fileModelMap.get(file);
		} else {
			for (Iterator itr = modelList.iterator(); itr.hasNext();) {
				JastAddModel model = (JastAddModel)itr.next();
				if (model.isModelFor(file)) {
					fileModelMap.put(file, model);
					return model;
				}
			}	
		}
		return null;
	}
	
	public static JastAddModel getModel(IDocument document) {
		for (Iterator itr = modelList.iterator(); itr.hasNext();) {
			JastAddModel model = (JastAddModel)itr.next();
			if (model.isModelFor(document)) {
				return model;
			}
		}		
		return null;
	}
	
	public static JastAddModel getModel(IJastAddNode node) {
		for (Iterator itr = modelList.iterator(); itr.hasNext();) {
			JastAddModel model = (JastAddModel)itr.next();
			if (model.isModelFor(node)) {
				return model;
			}
		}		
		return null;	
	}
	
	public static void addModel(JastAddModel model) {
		if (!modelList.contains(model)) {
			modelList.add(model);
		}
	}
	
	public static void removeModel(JastAddModel model) {
		if (modelList.contains(model)) {
			modelList.remove(model);
		}
	}

}
