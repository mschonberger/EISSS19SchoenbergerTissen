
const request = require('request');
const firebase = require('firebase-admin');

let firebaseConfig = {
    apiKey: "AIzaSyB859FNPUILv1Pfct7LRO6Z5bTZyyPsMz4",
    authDomain: "scrapofood.firebaseapp.com",
    databaseURL: "https://scrapofood.firebaseio.com",
    projectId: "scrapofood",
    storageBucket: "scrapofood.appspot.com",
    messagingSenderId: "127133367821",
    appId: "1:127133367821:web:d158373e5d7c059e"
};


// Initialize Firebase
firebase.initializeApp(firebaseConfig);

console.log('Hello and welcome to Scrap-O-Food!');
console.log('Please log in or register if you are a new user');

const readline = require('readline').createInterface({
    input: process.stdin,
    output: process.stdout
})

//asynchron network API (server/client)
const net = require('net');
let client = new net.Socket();

client.connect(1337, '127.0.0.1', function() {
    console.log('Connected');

    readline.question("Do you want to \r\n(1)log in \r\n(2)create a new user \r\n(3)Exit\r\n(4)weatherCall", function (selection) {
        switch (selection){
            case '1':
                console.log('login existing user');
                loginDialogue();
                break;
            case '2':
                console.log('register new user');
                registerUser();
                break;
            case '3':
                console.log('exit');
                client.end();
            case '4':
                weatherCall();
                break;
        }
    })
});

//Echoing what the Server receives
client.on('data', function(data) {
    console.log('Server: ' + data);
});

client.on('close', function() {
    console.log('Connection closed');
});

function weatherCall() {
    let apiKey = '5679f5659fe97f4a5084d206ddd88487';//y u no work? //${apiKey} funktioniert nicht. warum?
    let city = 'cologne';
    let url = 'https://api.openweathermap.org/data/2.5/forecast?q=cologne,DE&units=metric&appid=5679f5659fe97f4a5084d206ddd88487';

    request(url,function (err, response, body) {

        if(err){
            console.log('error: ',error);
        } else {
            //console.log('body:', body);
            let weatherData = JSON.parse(body);
            console.log(weatherData);
        }
    });
}

function loginDialogue() {

}
function registerUser() {

}