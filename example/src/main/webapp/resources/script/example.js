/*
  example.js - custom script for bitbucket-api-client-example
  Copyright (C) 2015 Nishimura Software Studio

  This program is free software: you can redistribute it and/or modify it
  under the terms of the GNU Affero General Public License as published by the
  Free Software Foundation, either version 3 of the License, or (at your
  option) any later version.
 
  This program is distributed in the hope that it will be useful, but WITHOUT
  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
  FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License
  for more details.
 
  You should have received a copy of the GNU Affero General Public License
  along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

"use strict";

$(document).ready(function () {
    var accountMenu = $("#example-account-menu");
    accountMenu.removeClass("pure-menu-allow-hover");
    accountMenu.mouseleave(function (event) {
        $("#example-account-menu-children").css("display", "");
    });

    $("#example-account-menu-link").click(function (event) {
        var children = $("#example-account-menu-children");
        if (children.css("display") !== "block") {
            children.css("display", "block");
        } else {
            accountMenu.mouseleave();
        }
        return false;
    });
});
