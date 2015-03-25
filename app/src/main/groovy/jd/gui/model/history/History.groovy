/*
 * Copyright (c) 2008-2015 Emmanuel Dupuy
 * This program is made available under the terms of the GPLv3 License.
 */

package jd.gui.model.history

class History {
    URI         current = null
    List<URI>   backward = []
    List<URI>   forward = []

    void add(URI uri) {
        if (current.equals(uri)) {
            // Already stored -> Quit
            return
        }

        if (current == null) {
            // Init history
            forward.clear()
            current = uri
            return
        }

        if (uri.path.toString().equals(current.path.toString())) {
            if (uri.fragment == null) {
                // Ignore
            } else if (current.fragment == null) {
                // Replace current URI
                current = uri
            } else {
                // Store URI
                forward.clear()
                backward.add(current)
                current = uri
            }
            return
        }

        if (uri.toString().startsWith(current.toString())) {
            // Replace current URI
            current = uri
            return
        }

        // Store URI
        forward.clear()
        backward.add(current)
        current = uri
    }

    URI backward() {
        if (! backward.isEmpty()) {
            forward.add(current)
            int size = backward.size()
            current = backward.remove(size-1)
        }
        return current
    }

    URI forward() {
        if (! forward.isEmpty()) {
            backward.add(current)
            int size = forward.size()
            current = forward.remove(size-1)
        }
        return current
    }

    boolean canBackward() { !backward.isEmpty() }
    boolean canForward() { !forward.isEmpty() }
}
