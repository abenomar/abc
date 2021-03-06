/**
 *  ServingXML
 *  
 *  Copyright (C) 2006  Daniel Parker
 *    daniel.parker@servingxml.com 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 * 
 **/

package com.servingxml.components.inverserecordmapping;

import com.servingxml.app.ParameterDescriptor;
import com.servingxml.util.Name;
import com.servingxml.ioc.components.ConfigurationContext;
import com.servingxml.util.ServingXmlException;
import com.servingxml.util.MessageFormatter;
import com.servingxml.util.ServingXmlMessages;
import com.servingxml.util.xml.Matchable;
import com.servingxml.components.string.StringFactoryCompiler;

/**
 * An assembler class for creating <tt>Matchable</tt> instances.
 * 
 * 
 * @author Daniel A. Parker (daniel.parker@servingxml.com)
 */

public class SubtreeFieldMapAssembler {

  private ParameterDescriptor[] parameterDescriptors = ParameterDescriptor.EMPTY_ARRAY;
  private Name fieldName = null;
  private String matchExpr = "";
  private String selectExpr = "";
  private ShredXmlFactory[] childFlattenerFactories = new ShredXmlFactory[0];

  public void injectComponent(ParameterDescriptor[] parameterDescriptors) {

    this.parameterDescriptors = parameterDescriptors;
  }

  public void setSelect(String selectExpr) {
    this.selectExpr = selectExpr;
  }

  public void setMatch(String matchExpr) {
    this.matchExpr = matchExpr;
  }

  public void setField(Name fieldName) {
    this.fieldName = fieldName;
  }

  public void injectComponent(ShredXmlFactory[] childFlattenerFactories) {
    this.childFlattenerFactories = childFlattenerFactories;
  }

  public SubtreeFieldMap assemble(ConfigurationContext context) {
   //System.out.println(getClass().getName()+".assemble field="+fieldName);

    if (fieldName == null) {
      String message = MessageFormatter.getInstance().getMessage(ServingXmlMessages.COMPONENT_ATTRIBUTE_REQUIRED,
        context.getElement().getTagName(),"field");
      throw new ServingXmlException(message);
    }

    SubtreeFieldMap subtreeFieldMap;
    if (selectExpr.length() == 0 && childFlattenerFactories.length > 0) {
     //System.out.println(getClass().getName()+".assemble field map contains record");
     //System.out.println("matchExpr="+matchExpr);
      subtreeFieldMap = childFlattenerFactories[0].createSubtreeFieldMap(fieldName, matchExpr);
    } else if (selectExpr.length() > 0) {
      subtreeFieldMap = new SubtreeFieldMapImpl(fieldName, matchExpr, selectExpr);
    } else {
      String message = MessageFormatter.getInstance().getMessage(ServingXmlMessages.COMPONENT_ATTRIBUTE_REQUIRED,
        context.getElement().getTagName(),"select");
      throw new ServingXmlException(message);
    }

    return subtreeFieldMap;
  }
}

