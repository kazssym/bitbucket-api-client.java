// example.js - app-specific script
// Copyright (C) 2015-2018 Kaz Nishimura
//
// Copying and distribution of this file, with or without modification, are
// permitted in any medium without royalty provided the copyright notice and
// this notice are preserved.  This file is offered as-is, without any warranty.

"use strict";

(() => {
    const MENU_CLASS_NAME = "pure-menu-allow-hover";
    const LINK_CLASS_NAME = "pure-menu-link";

    let menus = document.getElementsByClassName(MENU_CLASS_NAME);
    for (let menu of menus) {
        const showChildren = (visible) => {
            let classList = menu.className.split(/\s+/)
                .filter((name) => name != MENU_CLASS_NAME);
            if (visible) {
                classList.push(MENU_CLASS_NAME);
            }
            menu.className = classList.join(" ");
        };

        showChildren(false);
        menu.addEventListener("mouseleave", function () {
                showChildren(false);
            });
        for (let child of menu.childNodes) {
            if (typeof child.className === "string"
                && child.className.split(/\s+/).includes(LINK_CLASS_NAME)) {
                child.addEventListener("click", function (event) {
                        showChildren(true);
                        event.preventDefault();
                    });
            }
        }
    }
})();
