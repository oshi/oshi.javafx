/**
 * OSHI (https://github.com/oshi/oshi)
 *
 * Copyright (c) 2010 - 2019 The OSHI Project Team:
 * https://github.com/oshi/oshi/graphs/contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package oshi.javafx.scene.main;

import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;

/**
 * The controller for the main menu which contains top-level categories.
 */
public class MenuController {

    @FXML
    private MenuCategory baseboard;

    @FXML
    private MenuCategory display;

    @FXML
    private MenuCategory memory;

    @FXML
    private MenuCategory operating_system;

    @FXML
    private MenuCategory network;

    @FXML
    private MenuCategory power;

    @FXML
    private MenuCategory process;

    @FXML
    private MenuCategory processor;

    @FXML
    private MenuCategory sensor;

    @FXML
    private MenuCategory sound_card;

    @FXML
    private MenuCategory storage;

    @FXML
    private MenuCategory usb;

    /**
     * The currently selected menu item.
     */
    private ObjectProperty<MenuCategory> selected = new SimpleObjectProperty<>();

    @FXML
    private void initialize() {

        // Add listeners to ensure only one category appears selected at a time
        selected.addListener((p, o, n) -> {
            if (o != null)
                o.setExpanded(false);
        });

        for (MenuCategory item : List.of(baseboard, display, memory, operating_system, network, power, process,
                processor, sensor, sound_card, storage, usb)) {
            item.expandedProperty().addListener((p, o, n) -> {
                if (item.isExpanded())
                    selected.set(item);
            });
        }
    }
}