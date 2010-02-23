/*******************************************************************************
 * Copyright (c) 2009, 2010 Progress Software Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.fusesource.tools.messaging.cnf.model;

import org.eclipse.core.resources.IFile;

/**
 * Represents Listeners Root Component in CNF
 */
public class ListenersRootComponent extends BaseGroupComponent {

    private Object[] children;

    public ListenersRootComponent(String name, IFile file1) {
        super(name, file1);
    }

    public Object[] getChildren() {
        return children;
    }

    public void setChildren(Object[] children) {
        this.children = children;
    }

}
