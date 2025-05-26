package org.dasher.speed.base.ui.view.events;

import org.dasher.speed.base.domain.Contact;

@FunctionalInterface
public interface ContactEventListener {
    void onContactEvent(Contact contact);
}
