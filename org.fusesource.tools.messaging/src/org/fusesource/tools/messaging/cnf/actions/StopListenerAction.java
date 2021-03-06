/*******************************************************************************
 * Copyright (c) 2009, 2010 Progress Software Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/

package org.fusesource.tools.messaging.cnf.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.fusesource.tools.messaging.MessagingException;
import org.fusesource.tools.messaging.cnf.model.BaseGroupComponent;
import org.fusesource.tools.messaging.cnf.model.DataModelManager;
import org.fusesource.tools.messaging.cnf.model.ListenerComponent;
import org.fusesource.tools.messaging.core.IListener;

public class StopListenerAction implements IObjectActionDelegate {

    private ListenerComponent listenerComponent;

    public void setActivePart(IAction arg0, IWorkbenchPart arg1) {
    }

    public void run(IAction arg0) {
        try {
            IListener activeListener = listenerComponent.getListener();
            activeListener.stop();
            activeListener.setReceive(false);
            IFile fileToSave = ((BaseGroupComponent) listenerComponent.getParent()).getFile();
            DataModelManager.getInstance().saveListeners(fileToSave);
        } catch (MessagingException me) {
            me.printStackTrace();
            MessageDialog.openError(Display.getDefault().getActiveShell(), me.getMessage(), me.getCause() != null ? me
                    .getCause().getMessage() : "");
        }
    }

    public void selectionChanged(IAction action, ISelection selection) {
        if (((StructuredSelection) selection).size() > 1) {
            action.setEnabled(false);
            return;
        }
        Object firstElement = ((StructuredSelection) selection).getFirstElement();
        if (!(firstElement instanceof ListenerComponent)) {
            return;
        }
        listenerComponent = ((ListenerComponent) firstElement);
        boolean canEnable = listenerComponent.getListener().isStarted() && listenerComponent.getListener().canReceive();
        action.setEnabled(canEnable);
    }
}
