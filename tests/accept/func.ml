int _foo (int k) {
    return k;
}

int[10] foo (int k) {
    int[10] lots;
    int x;
    x = 9;
    lots[x] = 4;
    return lots;
}

string[3] _strALL3 (string k, int j) {
    string[3] three;
    three[0] = "hello";
    three[1] = "myard";
    three[2] = "foobar";
    return three;
}

string strABC (string _yam, int fire ) {
    return "{abcdef99}";
}

float f9_7 (float foo) {
    return 1.0;
}

float[1] floater1_ () {
    float[1] foo;
    foo[0] = 1001.2;
    return foo;
}

char alphaMy ( char let ) {
    return let + 10;
}

char[4] loopback () {
    char[4] mmm;
    mmm[0] = 'a';
    mmm[1] = 'b';
    mmm[2] = 'c';
    mmm[3] = 'd';
}

void main () {
    print _foo(10) + _foo(12);
}
