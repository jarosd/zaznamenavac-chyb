function universalAjaxPOST(data, funkcia, url) {
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = funkcia;

    xmlhttp.open("POST", url, true);
    xmlhttp.setRequestHeader("Content-Type", "application/json");
    // xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
    xmlhttp.send(data);
}

function universalAjaxGET(funkcia, url) {
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = funkcia;

    xmlhttp.open("GET", url, true);
    xmlhttp.send();
}

function akcieBug(event, url) {
    var chybaID = parseInt(event.target.getAttribute("data-chybaid"));
    var data = JSON.stringify({"cisloChyby": chybaID});

    var funkcia = function () {
        if (this.readyState === 4 && this.status === 200) {
            document.getElementById("statusAJAX").innerHTML = this.responseText;
            setTimeout(function() {
                window.location.reload();
            }, 2000);
        }
    };

    universalAjaxPOST(data, funkcia, url);
}

function pridajAkciuVymaz(event) {
    akcieBug(event, "/vymazatChybu");
}

function pridajAkciuNevyriesena(event) {
    akcieBug(event, "/oznacChybuZaNevyriesenu");
}

function pridajAkciuVyriesena() {
    akcieBug(event, "/oznacChybuZaVyriesenu");
}

function pridavanieAkcii(nazovTriedy, akcia) {
    if (document.getElementsByClassName(nazovTriedy)) {
        for (var i = 0; i < document.getElementsByClassName(nazovTriedy).length; i++) {
            document.getElementsByClassName(nazovTriedy)[i].addEventListener("click", function(event) {
                akcia(event);
            });
        }
    }
}

function pridajAkcieBug() {
   pridavanieAkcii("vymazatChybu", pridajAkciuVymaz);
   pridavanieAkcii("nevyriesenaChybaTlacidlo", pridajAkciuNevyriesena);
   pridavanieAkcii("vyriesenaChybaTlacidlo", pridajAkciuVyriesena);
}

function skontrolujNickObsadeny(nick) {
    var url = "/skontrolujNick/" + nick;

    var funkcia = function () {
        if (this.readyState === 4 && this.status === 200) {
            document.getElementById("statusMini").innerHTML = this.responseText;
        }
    };

    universalAjaxGET(funkcia, url);
}

function pridajAkciuNick() {
    if (document.getElementById("nick")) {
        document.getElementById("nick").addEventListener("change", function() {
            var zadanyNick = document.getElementById("nick").value;
            if (zadanyNick != "") {
                skontrolujNickObsadeny(zadanyNick);
            }
        });
    }
}

function filterZmeny() {
    if (document.getElementById("aktualizujFilter")) {
        document.getElementById("aktualizujFilter").addEventListener("click", function() {
            var pouzivatel = document.getElementById("pouzivatel").value;

            if (pouzivatel != "") {
                window.location.href = "/zmenyPortal?autor=" + pouzivatel;
            } else {
                window.location.href = "/zmenyPortal";
            }
        });
    }
}

function vymazKomentar() {
    var komentarID = parseInt(event.target.getAttribute("data-komentar"));
    var data = JSON.stringify({"idKomentara": komentarID});
    var url = "/vymazatKomentar";

    var funkcia = function () {
        if (this.readyState === 4 && this.status === 200) {
            document.getElementById("statusAJAX").outerHTML = this.responseText;
            setTimeout(function() {
                window.location.reload();
            }, 2000);
        }
    };

    universalAjaxPOST(data, funkcia, url);
}

function pridatAkciuVymazKomentar() {
    pridavanieAkcii("vymazatKomentar", vymazKomentar);
}

function editujKomentarPanel() {
    var komentarID = parseInt(event.target.getAttribute("data-komentar"));
    var url = "/editujKomentarPanel/" + komentarID;

    var funkcia = function () {
        if (this.readyState === 4 && this.status === 200) {
            document.getElementById("komentarModifikacia").innerHTML = this.responseText;
        }
    };

    universalAjaxGET(funkcia, url);
}

function pridatAkciuEditujKomentar() {
    pridavanieAkcii("editujKomentar", editujKomentarPanel);
}

window.addEventListener("load", function() {
    pridajAkcieBug();
    pridatAkciuVymazKomentar();
    pridatAkciuEditujKomentar();
    filterZmeny();
    pridajAkciuNick();
});