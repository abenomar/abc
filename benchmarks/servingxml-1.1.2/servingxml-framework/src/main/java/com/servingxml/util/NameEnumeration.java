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

package com.servingxml.util;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class NameEnumeration {

  private final Name[] names;

  public NameEnumeration() {
    this.names = new Name[0];
  }

  public NameEnumeration(Name[] names) {
    this.names = names;
  }

  public static NameEnumeration parse(QnameFactory context, String spaceSeparatedList) {
    StringTokenizer elementNameTokenizer = new StringTokenizer(spaceSeparatedList," ");
    ArrayList<Name> elementNameList = new ArrayList<Name>();
    while (elementNameTokenizer.hasMoreTokens()) {
      String elementQname = elementNameTokenizer.nextToken();
      Name elementName = context.createName(elementQname.trim());
      elementNameList.add(elementName);
    }
    Name[] names = new Name[elementNameList.size()];
    names = (Name[])elementNameList.toArray(names);

    return new NameEnumeration(names);
  }

  public boolean isEmpty() {
    return names.length == 0;
  }

  public Name[] getNames() {
    return names;
  }
}

