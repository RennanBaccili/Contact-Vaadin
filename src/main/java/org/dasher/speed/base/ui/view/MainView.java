package org.dasher.speed.base.ui.view;

import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.PermitAll;

/**
 * This view shows up when a user navigates to the root ('/') of the
 * application.
 */
@Route
@PermitAll // When security is enabled, allow all authenticated users
public final class MainView extends Main {
    MainView() {

    }  
        
            /**
     * Navigates to the main view.
     */
    public static void showMainView() {
    }
}
