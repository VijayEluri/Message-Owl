/*******************************************************************************
 * Copyright (c) 2009, 2010 Progress Software Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.fusesource.tools.messaging.cnf.ui;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.resource.CompositeImageDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.wst.common.project.facet.core.FacetedProjectFramework;
import org.fusesource.tools.messaging.IConstants;
import org.fusesource.tools.messaging.server.MessagingServersUtil;
import org.fusesource.tools.messaging.ui.AbstractDecorator;
import org.fusesource.tools.messaging.ui.ImageConstants;
import org.fusesource.tools.messaging.utils.ImagesUtil;

/**
 * Handles the decorations for the Messaging Project based on its state
 */
public class MsgProjectDecorator extends AbstractDecorator {
    private static Image offlineImage = ImagesUtil.getInstance().getImage(ImageConstants.PROJECT_OFFLINE_IMAGE);
    private static Image onlineImage = ImagesUtil.getInstance().getImage(ImageConstants.PROJECT_ONLINE_IMAGE);

    public Image decorateImage(Image baseImage, Object element) {
        if (element instanceof IProject) {
            return getDecorationImage(baseImage, (IProject) element);
        }
        return null;
    }

    private Image getDecorationImage(Image baseImage, IProject element) {
        try {
            boolean isMsgFacetedPrj = FacetedProjectFramework.hasProjectFacet(element, IConstants.MSG_PRJ_FACET);

            if (!isMsgFacetedPrj) {
                return null;
            }

            if (MessagingServersUtil.isMsgProjectOnline(element)) {
                return getOverlayImage(ImageConstants.ONLINE_DECORATOR_DESC_ID, baseImage, onlineImage);
            } else {
                return getOverlayImage(ImageConstants.OFFLINE_DECORATOR_DESC_ID, baseImage, offlineImage);
            }

        } catch (CoreException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String decorateText(String text, Object element) {
        return null;
    }

    private class OverlayIcon extends CompositeImageDescriptor {
        private final Image decoratorImag;
        private final Image baseImage;

        public OverlayIcon(Image baseImage, Image decoratorImag) {
            this.baseImage = baseImage;
            this.decoratorImag = decoratorImag;
        }

        @Override
        protected void drawCompositeImage(int width, int height) {
            drawImage(baseImage.getImageData(), 0, 0);
            ImageData overlayImageData = decoratorImag.getImageData();
            drawImage(overlayImageData, 0, 0);
        }

        @Override
        protected Point getSize() {
            return new Point(baseImage.getBounds().width, baseImage.getBounds().height);
        }

    }

    @Override
    protected ImageDescriptor getOverlayImageDescriptor(Image baseImage, Image decoratorImage) {
        return new OverlayIcon(baseImage, decoratorImage);
    }
}
