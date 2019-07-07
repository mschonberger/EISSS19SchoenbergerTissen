var express = require('express');
var app = express();
var fs = require("fs");


var bodyParser = require('body-parser')
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
    extended: true
}));
app.use(express.static(__dirname + '/categoryThumbs'));
app.use(express.static(__dirname + '/uploadThumbs'));

let globalPort = "1337";
let picFormat = '.jpg';

app.get('/categories', function (req, res) {
    let count = req.query.count !== undefined ? req.query.count : req.query.count = 100;
    if (req.query.country) {
        let countrySpots = categories.filter(function (destination) {
            return destination.country === req.query.country
        });
        res.end(JSON.stringify(countrySpots.slice(0, count)));
    }
    res.status(200).json({categories: categories.slice(0, count)});

    //console.log("What he gets:\n\r");
    //console.log(categories);
});

// Get one particular Destination using ID
app.get('/meals/:strCategory', function (req, res) {
    let tempMeals = [];
    for (let i = 0; i < meals.length; i++) {
        if (meals[i].strCategory === req.params.strCategory) {
            tempMeals.push(meals[i]);
        }
    }
    res.json(JSON.stringify(tempMeals));
});

app.get('/meals', function (req, res) {
    let tempMeals = {meals: []};
    let count = req.query.count !== undefined ? req.query.count : req.query.count = 100;

    console.log(req.query);

    if (req.query.strCategory != null) {
        for (let i = 0; i < meals.length; i++) {
            tempMeals.meals = meals.filter(function (meals) {
                return meals.strCategory === req.query.strCategory
            });
        }
    }
    if (req.query.idGroup != null) {
        for (let i = 0; i < meals.length; i++) {
            if (tempMeals.meals.length > 0) {
                tempMeals.meals = tempMeals.meals.filter(function (meals) {
                    return meals.idGroup === req.query.idGroup
                });
            } else {
                tempMeals.meals = meals.filter(function (meals) {
                    return meals.idGroup === req.query.idGroup
                });
            }
        }
    }
    if (req.query.strMeal != null) {
        for (let i = 0; i < meals.length; i++) {
            tempMeals.meals = meals.filter(function (meals) {
                return meals.strMeal === req.query.strMeal
            });
        }
    }
    if (tempMeals.meals.length > 0) {
        res.status(200).json(sortMeals(tempMeals));
    } else {
        res.status(200).json(sortMeals(meals));
    }
});

/**
 * Sorts given meals array
 * @param meals
 * @returns {*}
 */
function sortMeals(meals) {
    meals.meals.sort(function (a, b) {
        return b.rating - a.rating;
    });
    return meals;
}

//inserat erstellen
app.post('/meals', function (req, res) {
    let mealsIndex = meals.length;
    let newMeal = {
        "idMeal": mealsIndex,
        "idUser": req.body.idUser,
        "idGroup": req.body.idGroup,
        "strMeal": req.body.strMeal,
        "strDescription": req.body.strDescription,
        "strLongitude": req.body.strLongitude,
        "strLatitude": req.body.strLatitude,
        "adDate": req.body.adDate,
        "strCategory": req.body.strCategory,
        "strStoring": req.body.strStoring,
        "strCondition": req.body.strCondition,
        "strMHD": req.body.strMHD,
        "strMealThumb": "http://" + serverIP + ":" + globalPort + "/upload" + req.body.strCategory + picFormat,
        "strMail": req.body.strMail,
        "strPhone": req.body.strPhone,
        "rating": createRating(req.body.strCategory, req.body.strStoring, req.body.strCondition)
    };

    newMeal.strMHD = getMHD(newMeal.strMHD, newMeal.rating);
    meals.push(newMeal);
    res.status(201).json(newMeal);

    console.log(newMeal);
});

/**
 * Calculates a best before date with user given date plus our calculated rating
 * @param {Date} date to add rating to
 * @param {rating} rating which will be added to the date
 * @returns {date} Returns a new date
 */
function getMHD(date, rating) {
    let tempDate = new Date(date);
    return tempDate.addDays(rating);
}

//Datatype extention for adding days to a date
Date.prototype.addDays = function (days) {
    var date = new Date(this.valueOf());
    date.setDate(date.getDate() + days);
    return date.toLocaleDateString("de-DE");
};

/**
 * Generates a rating for meals how long the last.
 * @param {String} category req.body.strCategory
 * @param {String} storing req.body.strStoring
 * @param {String} condition req.body.strCondition
 * @returns {number} Returns the calculated rating number
 */
function createRating(category, storing, condition) {
    let retVal = 0;

    for (let i = 0; i < category.length; i++) {
        retVal += getStoringConditionValues(i, storing, condition);
    }
    //TODO: retVal += getStoringConditionValues(i, storing, condition); für jede Kategorie damit es ein realistischeres Rating gibt
    switch (category) {
        case'Getraenke':
            retVal += 1;
            break;
        case'Nudeln':
            retVal += 3;
            break;
        case'Backwaren':
            retVal += 1;
            break;
        case'Fleischwaren':
            retVal += 1;
            break;
        case'Obst':
            retVal += 1;
            break;
        case'Gemuese':
            retVal += 2;
            break;
        case'Mahlzeiten':
            retVal += 1;
            break;
        case'Snacks':
            retVal += 4;
            break;
        case'Suessigkeiten':
            retVal += 4;
            break;
        case'Milchprodukte':
            retVal += 1;
            break;
        case'Gewuerze':
            retVal += 4;
            break;
        case'Sonstiges':
            retVal += 1;
            break;
    }
    return retVal;
}

function getStoringConditionValues(category, storing, condition) {
    let retVal = 0;
    switch (storing) {
        case'protected':
            retVal += parseInt(categories[category].ValueStorageProtected);
            break;
        case'freezer':
            retVal += parseInt(categories[category].ValueStorageFreezer);
            break;
        case'fridge':
            retVal += parseInt(categories[category].ValueStorageFridge);
            break;
        case'opened':
            retVal += parseInt(categories[category].ValueStorageOpen);
            break;
    }
    switch (condition) {
        case'closed':
            retVal += parseInt(categories[category].ValueConditionClosed);
            break;
        case'fresh':
            retVal += parseInt(categories[category].ValueConditionFresh);
            break;
        case'frozen':
            retVal += parseInt(categories[category].ValueConditionFrozen);
            break;
        case'opened':
            retVal += parseInt(categories[category].ValueConditionOpen);
            break;
    }
    return retVal;
}

// Home Page
app.get('/', (req, res) => res.send('Welcome!'));

let servIpPublic = '93.214.253.20';
let serverIpHome = '192.168.178.20';
let serverIpTH = '10.3.82.190';
let serverIpSmartphone = '192.168.43.236';

let serverIP = serverIpHome;

// Configure server
let server = app.listen(1337, serverIP, function (req, res) {

    let host = server.address().address
    let port = server.address().port

    console.log(`Server running at http://${host}:${port}/`);

    server.on('connection', () => {
        //console.log(file);
    })
    server.on('data', (data) => {
        console.log(data);
    })
});

let categories =
    [{
        "idCategory": "0",
        "strCategory": "Getraenke",
        "strCategoryThumb": "http://" + serverIP + ":1337/Getraenke.png",
        "strCategoryDescription": "Je nach Getränkesorte variiert auch die Haltbarkeit, die bei Wein oder bestimmten Alkoholsorten durch eine optimierte Lagerung sogar verlängert werden kann. In vielen Fällen sind Getränke nach Ablauf des Mindesthaltbarkeitsdatums auch weiterhin genießbar, jedoch sollte der Verbraucher die Produkte stets auf Geruch, Aussehen und Geschmack prüfen.",
        "ValueStorageOpen": "1",
        "ValueStorageFridge": "4",
        "ValueStorageFreezer": "1",
        "ValueStorageProtected": "2",
        "ValueConditionOpen": "1",
        "ValueConditionClosed": "3",
        "ValueConditionFresh": "1",
        "ValueConditionFrozen": "1"
    }, {
        "idCategory": "1",
        "strCategory": "Nudeln",
        "strCategoryThumb": "http://192.168.178.20:1337/Nudeln.png",
        "strCategoryDescription": "Harte, getrocknete Nudeln sind auch lange nach Ablauf des MHD noch genießbar. AUSNAHME: Eiernudeln sollte man nach dem abgelaufenem Mindeshaltbarkeitsdatum nicht mehr verwenden! Frische Pastanudeln halten bei Lagerung im Kühlschrank 3 bis 4 Wochen. Die meisten Getreideprodukte sind nach Ablauf des Mindesthaltbarkeitsdatums weiterhin genießbar. Durch eine geeignete Lagerung kann deren Haltbarkeit sogar noch verlängert werden. In der Regel gilt jedoch, dass sämtliche Getreideerzeugnisse nach längerem Ablauf des MHD zunächst auf Optik, Geruch und Schimmelbefall sowie folgend auf ihren Geschmack geprüft werden sollten.",
        "ValueStorageOpen": "2",
        "ValueStorageFridge": "1",
        "ValueStorageFreezer": "1",
        "ValueStorageProtected": "4",
        "ValueConditionOpen": "2",
        "ValueConditionClosed": "4",
        "ValueConditionFresh": "1",
        "ValueConditionFrozen": "1"
    }, {
        "idCategory": "2",
        "strCategory": "Backwaren",
        "strCategoryThumb": "http://192.168.178.20:1337/Backwaren.png",
        "strCategoryDescription": "Die Haltbarkeitsdauer von Brot muss der Verbraucher in Bezug auf zwei verschiedene „Arten“ des Lebensmittels betrachten. Einerseits die abgepackte Ware aus industrieller Herstellung, andererseits das frische Brot vom Bäcker. Klar ist, dass das Bäckerbrot, das ohne Aufdruck eines Mindesthaltbarkeitsdatums verkauft werden darf, schneller verdirbt als industriell gefertigte Backwaren. Dennoch kann man die Haltbarkeit mit einer wohl überdachten Aufbewahrung positiv beeinflussen. Durch eingesetzten Enzyme ist das industriell hergestellte Brot bis zu 8 Wochen haltbar, bei frischem Brot vom Bäcker ist drauf zu achten welche Sorte gekauft wurde. Allgemein ist eine Haltbarkeit von 3-5 Tagen einplanbar. ",
        "ValueStorageOpen": "1",
        "ValueStorageFridge": "1",
        "ValueStorageFreezer": "5",
        "ValueStorageProtected": "4",
        "ValueConditionOpen": "1",
        "ValueConditionClosed": "3",
        "ValueConditionFresh": "1",
        "ValueConditionFrozen": "5"
    }, {
        "idCategory": "3",
        "strCategory": "Fleischwaren",
        "strCategoryThumb": "http://192.168.178.20:1337/Fleischwaren.png",
        "strCategoryDescription": "Frisches Fleisch sollte schnell nach dem Einkauf weiterverarbeitet werden, und auch dann recht zügig konsumiert werden.",
        "ValueStorageOpen": "1",
        "ValueStorageFridge": "2",
        "ValueStorageFreezer": "4",
        "ValueStorageProtected": "1",
        "ValueConditionOpen": "1",
        "ValueConditionClosed": "2",
        "ValueConditionFresh": "1",
        "ValueConditionFrozen": "4"
    }, {
        "idCategory": "4",
        "strCategory": "Obst",
        "strCategoryThumb": "http://192.168.178.20:1337/Obst.png",
        "strCategoryDescription": "Frisches Obst gehört zu den schnell verderblichen Lebensmitteln und weist aus diesem Grund auch eine geringe Haltbarkeit auf. Laut §7 des LMKV sind derartige Produkte von der Kennzeichnung mit Mindesthaltbarkeitsdatum ausgenommen. Äpfel sollten getrennt von anderen Gemüse- und Obstsorten gelagert werden (setzen Pflanzenhormon Ethylen frei und beschleunigen den Reifungsprozess)",
        "ValueStorageOpen": "1",
        "ValueStorageFridge": "1",
        "ValueStorageFreezer": "2",
        "ValueStorageProtected": "1",
        "ValueConditionOpen": "1",
        "ValueConditionClosed": "1",
        "ValueConditionFresh": "2",
        "ValueConditionFrozen": "2"
    }, {
        "idCategory": "5",
        "strCategory": "Gemuese",
        "strCategoryThumb": "http://192.168.178.20:1337/Gemuese.png",
        "strCategoryDescription": "Frisches Gemüse gehört zu den schnell verderblichen Lebensmitteln und weist aus diesem Grund auch eine geringe Haltbarkeit auf. Laut §7 des LMKV sind derartige Produkte von der Kennzeichnung mit Mindesthaltbarkeitsdatum ausgenommen. Tomaten sollten getrennt von anderen Gemüse- und Obstsorten gelagert werden (setzen Pflanzenhormon Ethylen frei und beschleunigen den Reifungsprozess)",
        "ValueStorageOpen": "1",
        "ValueStorageFridge": "1",
        "ValueStorageFreezer": "2",
        "ValueStorageProtected": "1",
        "ValueConditionOpen": "1",
        "ValueConditionClosed": "1",
        "ValueConditionFresh": "2",
        "ValueConditionFrozen": "2"
    }, {
        "idCategory": "6",
        "strCategory": "Mahlzeiten",
        "strCategoryThumb": "http://192.168.178.20:1337/Mahlzeiten.png",
        "strCategoryDescription": "Gekochte Mahlzeiten sind in der Regel nicht lange haltbar und sollten nach Anfertigung zügig konsumiert werden.",
        "ValueStorageOpen": "1",
        "ValueStorageFridge": "2",
        "ValueStorageFreezer": "3",
        "ValueStorageProtected": "1",
        "ValueConditionOpen": "1",
        "ValueConditionClosed": "1",
        "ValueConditionFresh": "3",
        "ValueConditionFrozen": "3"
    }, {
        "idCategory": "7",
        "strCategory": "Snacks",
        "strCategoryThumb": "http://192.168.178.20:1337/Snacks.png",
        "strCategoryDescription": "Snacks sind in der Regel auch nach dem Ablauf des Mindeshaltbarkeitsdatum genießbar, sollten jedoch zügig nach dem Öffnen konsumiert werden",
        "ValueStorageOpen": "1",
        "ValueStorageFridge": "1",
        "ValueStorageFreezer": "1",
        "ValueStorageProtected": "3",
        "ValueConditionOpen": "1",
        "ValueConditionClosed": "3",
        "ValueConditionFresh": "2",
        "ValueConditionFrozen": "1"
    }, {
        "idCategory": "8",
        "strCategory": "Suessigkeiten",
        "strCategoryThumb": "http://192.168.178.20:1337/Suessigkeiten.png",
        "strCategoryDescription": "Snacks sind in der Regel auch nach dem Ablauf des Mindeshaltbarkeitsdatum genießbar, sollten jedoch zügig nach dem Öffnen konsumiert werden",
        "ValueStorageOpen": "1",
        "ValueStorageFridge": "2",
        "ValueStorageFreezer": "2",
        "ValueStorageProtected": "2",
        "ValueConditionOpen": "1",
        "ValueConditionClosed": "2",
        "ValueConditionFresh": "2",
        "ValueConditionFrozen": "2"
    }, {
        "idCategory": "9",
        "strCategory": "Milchprodukte",
        "strCategoryThumb": "http://192.168.178.20:1337/Milchprodukte.png",
        "strCategoryDescription": "Die Haltbarkeit von Milch und Milchprodukten in ungeöffneten Verpackungen ist im Großteil der Fälle auf nach abgelaufenem Mindesthaltbarkeitsdatum gegeben. Vorsicht geboten ist jedoch bei derartigen Lebensmitteln in angebrochenen Verpackungen, Milch kann schnell sauer und Butter ranzig werden, Käse austrocknen.",
        "ValueStorageOpen": "1",
        "ValueStorageFridge": "3",
        "ValueStorageFreezer": "2",
        "ValueStorageProtected": "2",
        "ValueConditionOpen": "1",
        "ValueConditionClosed": "3",
        "ValueConditionFresh": "1",
        "ValueConditionFrozen": "2"
    }, {
        "idCategory": "10",
        "strCategory": "Gewuerze",
        "strCategoryThumb": "http://192.168.178.20:1337/Gewuerze.png",
        "strCategoryDescription": "Gewürze haben eine sehr hohe Haltbarkeit",
        "ValueStorageOpen": "2",
        "ValueStorageFridge": "1",
        "ValueStorageFreezer": "1",
        "ValueStorageProtected": "2",
        "ValueConditionOpen": "2",
        "ValueConditionClosed": "3",
        "ValueConditionFresh": "2",
        "ValueConditionFrozen": "1"
    }, {
        "idCategory": "11",
        "strCategory": "Sonstiges",
        "strCategoryThumb": "http://192.168.178.20:1337/Sonstiges.png",
        "strCategoryDescription": "Für Lebensmittel in dieser Kategorie können unterschiedliche Bedingungen vorliegen. Informieren Sie sich anhand der Beschreibung über den Zustand",
        "ValueStorageOpen": "1",
        "ValueStorageFridge": "1",
        "ValueStorageFreezer": "1",
        "ValueStorageProtected": "1",
        "ValueConditionOpen": "1",
        "ValueConditionClosed": "1",
        "ValueConditionFresh": "1",
        "ValueConditionFrozen": "1"
    }];

let meals =
    [{
        "idMeal": "0",
        "idUser": "1",
        "idGroup": "1",
        "strMeal": "LeckereBackwaren",
        "strDescription": "What a nice looking meal.",
        "strLongitude": "72.344",
        "strLatitude": "23.243",
        "adDate": "06.07.2019",
        "strCategory": "Backwaren",
        "strStoring": "protected",
        "strCondition": "fresh",
        "strMHD": "28.07.2019",
        "strMealThumb": "http://" + serverIP + ":" + globalPort + "/uploadBackwaren" + picFormat,
        "strMail": "some1@ones-mail.com",
        "strPhone": "0123456789",
        "rating": '10'
    }, {
        "idMeal": "1",
        "idUser": "1",
        "idGroup": "2",
        "strMeal": "NochLeckerereBackwaren",
        "strDescription": "What a nice looking meal.",
        "strLongitude": "72.344",
        "strLatitude": "23.243",
        "adDate": "06.07.2019",
        "strCategory": "Backwaren",
        "strStoring": "fridge",
        "strCondition": "closed",
        "strMHD": "27.09.2019",
        "strMealThumb": "http://" + serverIP + ":" + globalPort + "/uploadBackwaren" + picFormat,
        "strMail": "some1@ones-mail.com",
        "strPhone": "0123456789",
        "rating": '9'
    }, {
        "idMeal": "2",
        "idUser": "2",
        "idGroup": "2",
        "strMeal": "MealTitle",
        "strDescription": "Delicious looking meal. ",
        "strLongitude": "72.344",
        "strLatitude": "23.243",
        "adDate": "06.07.2019",
        "strCategory": "Fleischwaren",
        "strStoring": "freezer",
        "strCondition": "frozen",
        "strMHD": "23.08.2019",
        "strMealThumb": "http://" + serverIP + ":" + globalPort + "/uploadFleischwaren" + picFormat,
        "strMail": "some2@ones-mail.com",
        "strPhone": "987654321",
        "rating": '5'
    }, {
        "idMeal": "3",
        "idUser": "1",
        "idGroup": "1",
        "strMeal": "Nachtisch",
        "strDescription": "What a nice looking meal.",
        "strLongitude": "72.344",
        "strLatitude": "23.243",
        "adDate": "06.07.2019",
        "strCategory": "Suessigkeiten",
        "strStoring": "protected",
        "strCondition": "fresh",
        "strMHD": "28.07.2019",
        "strMealThumb": "http://" + serverIP + ":" + globalPort + "/uploadSuessigkeiten" + picFormat,
        "strMail": "some1@ones-mail.com",
        "strPhone": "0123456789",
        "rating": '7'
    }, {
        "idMeal": "4",
        "idUser": "2",
        "idGroup": "1",
        "strMeal": "Blablabla Mister Freeman",
        "strDescription": "Delicious looking meal. ",
        "strLongitude": "72.344",
        "strLatitude": "23.243",
        "adDate": "07.07.2019",
        "strCategory": "Nudeln",
        "strStoring": "freezer",
        "strCondition": "frozen",
        "strMHD": "23.08.2019",
        "strMealThumb": "http://" + serverIP + ":" + globalPort + "/uploadNudeln" + picFormat,
        "strMail": "some2@ones-mail.com",
        "strPhone": "987654321",
        "rating": '8'
    }, {
        "idMeal": "5",
        "idUser": "2",
        "idGroup": "1",
        "strMeal": "Hot and Spicy is not nicey",
        "strDescription": "Delicious looking meal. ",
        "strLongitude": "72.344",
        "strLatitude": "23.243",
        "adDate": "07.07.2019",
        "strCategory": "Gewuerze",
        "strStoring": "freezer",
        "strCondition": "frozen",
        "strMHD": "23.08.2019",
        "strMealThumb": "http://" + serverIP + ":" + globalPort + "/uploadGewuerze" + picFormat,
        "strMail": "some2@ones-mail.com",
        "strPhone": "987654321",
        "rating": '4'
    }, {
        "idMeal": "6",
        "idUser": "2",
        "idGroup": "1",
        "strMeal": "Tomatoes",
        "strDescription": "Delicious looking meal. ",
        "strLongitude": "72.344",
        "strLatitude": "23.243",
        "adDate": "06.07.2019",
        "strCategory": "Gemuese",
        "strStoring": "freezer",
        "strCondition": "frozen",
        "strMHD": "23.08.2019",
        "strMealThumb": "http://" + serverIP + ":" + globalPort + "/uploadGemuese" + picFormat,
        "strMail": "some2@ones-mail.com",
        "strPhone": "987654321",
        "rating": '5'
    }, {
        "idMeal": "7",
        "idUser": "2",
        "idGroup": "1",
        "strMeal": "Hochprozentiger Alkohol",
        "strDescription": "Delicious looking meal. ",
        "strLongitude": "72.344",
        "strLatitude": "23.243",
        "adDate": "06.07.2019",
        "strCategory": "Getraenke",
        "strStoring": "freezer",
        "strCondition": "frozen",
        "strMHD": "23.08.2019",
        "strMealThumb": "http://" + serverIP + ":" + globalPort + "/uploadGetraenke" + picFormat,
        "strMail": "some2@ones-mail.com",
        "strPhone": "987654321",
        "rating": '6'
    }, {
        "idMeal": "8",
        "idUser": "2",
        "idGroup": "1",
        "strMeal": "Mahlzeit mit Milch",
        "strDescription": "Delicious looking meal. ",
        "strLongitude": "72.344",
        "strLatitude": "23.243",
        "adDate": "07.07.2019",
        "strCategory": "Mahlzeiten",
        "strStoring": "freezer",
        "strCondition": "frozen",
        "strMHD": "23.08.2019",
        "strMealThumb": "http://" + serverIP + ":" + globalPort + "/uploadMahlzeiten" + picFormat,
        "strMail": "some2@ones-mail.com",
        "strPhone": "987654321",
        "rating": '9'
    }, {
        "idMeal": "9",
        "idUser": "2",
        "idGroup": "1",
        "strMeal": "Milch macht müde Männer munter",
        "strDescription": "Delicious looking meal. ",
        "strLongitude": "72.344",
        "strLatitude": "23.243",
        "adDate": "07.07.2019",
        "strCategory": "Milchprodukte",
        "strStoring": "freezer",
        "strCondition": "frozen",
        "strMHD": "23.08.2019",
        "strMealThumb": "http://" + serverIP + ":" + globalPort + "/uploadMilchprodukte" + picFormat,
        "strMail": "some2@ones-mail.com",
        "strPhone": "987654321",
        "rating": '10'
    }, {
        "idMeal": "10",
        "idUser": "2",
        "idGroup": "1",
        "strMeal": "Obst ist lecker",
        "strDescription": "Delicious looking meal. ",
        "strLongitude": "72.344",
        "strLatitude": "23.243",
        "adDate": "07.07.2019",
        "strCategory": "Obst",
        "strStoring": "freezer",
        "strCondition": "frozen",
        "strMHD": "23.08.2019",
        "strMealThumb": "http://" + serverIP + ":" + globalPort + "/uploadObst" + picFormat,
        "strMail": "some2@ones-mail.com",
        "strPhone": "987654321",
        "rating": '5'
    }, {
        "idMeal": "11",
        "idUser": "2",
        "idGroup": "1",
        "strMeal": "Chips oder ähnliches",
        "strDescription": "Delicious looking meal. ",
        "strLongitude": "72.344",
        "strLatitude": "23.243",
        "adDate": "06.07.2019",
        "strCategory": "Snacks",
        "strStoring": "freezer",
        "strCondition": "frozen",
        "strMHD": "23.08.2019",
        "strMealThumb": "http://" + serverIP + ":" + globalPort + "/uploadSnacks" + picFormat,
        "strMail": "some2@ones-mail.com",
        "strPhone": "987654321",
        "rating": '7'
    }, {
        "idMeal": "12",
        "idUser": "2",
        "idGroup": "1",
        "strMeal": "Weiches Fleisch",
        "strDescription": "Delicious looking meal. ",
        "strLongitude": "72.344",
        "strLatitude": "23.243",
        "adDate": "06.07.2019",
        "strCategory": "Fleischwaren",
        "strStoring": "freezer",
        "strCondition": "frozen",
        "strMHD": "23.08.2019",
        "strMealThumb": "http://" + serverIP + ":" + globalPort + "/uploadFleischwaren" + picFormat,
        "strMail": "some2@ones-mail.com",
        "strPhone": "987654321",
        "rating": '6'
    }, {
        "idMeal": "13",
        "idUser": "2",
        "idGroup": "1",
        "strMeal": "Undefinierbares Grünes",
        "strDescription": "Not so delicious looking meal.",
        "strLongitude": "72.344",
        "strLatitude": "23.243",
        "adDate": "06.07.2019",
        "strCategory": "Sonstiges",
        "strStoring": "freezer",
        "strCondition": "frozen",
        "strMHD": "23.08.2019",
        "strMealThumb": "http://" + serverIP + ":" + globalPort + "/uploadSonstiges" + picFormat,
        "strMail": "some2@ones-mail.com",
        "strPhone": "987654321",
        "rating": '9'
    }, {
        "idMeal": "14",
        "idUser": "2",
        "idGroup": "1",
        "strMeal": "Sourcream makes me scream",
        "strDescription": "Delicious looking meal.",
        "strLongitude": "72.344",
        "strLatitude": "23.243",
        "adDate": "06.07.2019",
        "strCategory": "Snacks",
        "strStoring": "freezer",
        "strCondition": "frozen",
        "strMHD": "23.08.2019",
        "strMealThumb": "http://" + serverIP + ":" + globalPort + "/uploadSnacks" + picFormat,
        "strMail": "some2@ones-mail.com",
        "strPhone": "987654321",
        "rating": '5'
    }, {
        "idMeal": "15",
        "idUser": "2",
        "idGroup": "1",
        "strMeal": "Hähnchenfleisch",
        "strDescription": "Delicious meat.",
        "strLongitude": "72.344",
        "strLatitude": "23.243",
        "adDate": "06.07.2019",
        "strCategory": "Fleischwaren",
        "strStoring": "freezer",
        "strCondition": "frozen",
        "strMHD": "23.08.2019",
        "strMealThumb": "http://" + serverIP + ":" + globalPort + "/uploadFleischwaren" + picFormat,
        "strMail": "some2@ones-mail.com",
        "strPhone": "987654321",
        "rating": '5'
    }, {
        "idMeal": "16",
        "idUser": "2",
        "idGroup": "1",
        "strMeal": "Rindfleisch",
        "strDescription": "Delicious meat.",
        "strLongitude": "72.344",
        "strLatitude": "23.243",
        "adDate": "06.07.2019",
        "strCategory": "Fleischwaren",
        "strStoring": "freezer",
        "strCondition": "frozen",
        "strMHD": "23.08.2019",
        "strMealThumb": "http://" + serverIP + ":" + globalPort + "/uploadFleischwaren" + picFormat,
        "strMail": "some2@ones-mail.com",
        "strPhone": "987654321",
        "rating": '5'
    }, {
        "idMeal": "17",
        "idUser": "2",
        "idGroup": "1",
        "strMeal": "Kalbfleisch",
        "strDescription": "Delicious meat.",
        "strLongitude": "72.344",
        "strLatitude": "23.243",
        "adDate": "06.07.2019",
        "strCategory": "Fleischwaren",
        "strStoring": "freezer",
        "strCondition": "frozen",
        "strMHD": "23.08.2019",
        "strMealThumb": "http://" + serverIP + ":" + globalPort + "/uploadFleischwaren" + picFormat,
        "strMail": "some2@ones-mail.com",
        "strPhone": "987654321",
        "rating": '5'
    }
    ];
