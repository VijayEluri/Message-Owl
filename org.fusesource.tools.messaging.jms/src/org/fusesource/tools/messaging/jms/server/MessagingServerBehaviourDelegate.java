/*******************************************************************************
 * Copyright (c) 2009, 2010 Progress Software Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.fusesource.tools.messaging.jms.server;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.wst.server.core.IServer;
import org.eclipse.wst.server.core.model.ServerBehaviourDelegate;
import org.fusesource.tools.messaging.core.IConnection;
import org.fusesource.tools.messaging.server.MessagingServerDelegate;

public class MessagingServerBehaviourDelegate extends ServerBehaviourDelegate {

    @Override
    public void stop(boolean force) {
        MessagingServerDelegate serverDelegate = null;
        try {
            serverDelegate = (MessagingServerDelegate) getServer().loadAdapter(MessagingServerDelegate.class,
                    new NullProgressMonitor());
            IConnection connection = serverDelegate.getServerConfiguration().getProvider().getConnection();
            connection.closeConnection();
            getServer().stop(true);
            setState(IServer.STATE_STOPPED);
            System.out.println("[*] Server Stopped");
        } catch (Throwable e) {
            e.printStackTrace();
            if (serverDelegate != null) {
                setState(IServer.STATE_STOPPED);
            }
        }

    }

    /**
     * Set the state of the server based on the connection
     * 
     * @param currentState
     */
    public void setState(int currentState) {
        setServerState(currentState);
    }

}
