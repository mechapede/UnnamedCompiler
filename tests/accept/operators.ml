void operate_all ( int x, char y, string j, float z ) {
    int xxx;
    char zzz;
    float fff;
    fff = 10 + z * 3;
    if ( z * 3 + 11 < fff ) {
        println "Precedence of float failed, unless overflow!";
    }
    zzz = zzz + 1;
    xxx = x + 101 * 3 * 2;
}

//a normal comment to test this
void main () {
    int x;
    int y;
    x = 1 + 3*4*11 + 7 + 6 * 2;
    y = 3*4*11 + 1 + 7 + 6 * 2;
    if ( x == y ) {

    } else {
        println "Pres problems with PLUS and MINUS";
    }

    if( 1 < 2 == 1 < 2 ) {

    } else {
        print "Something wrong with LESSTHAN and EQUALEQUAL";
    }

    if ( 2 * 4 + 3 == 3 + 2 * 4 ) {

    } else {
        print "Issues with PLUS, MULTI and EQUALEQUAL";
    }

    if ( 2 * 4 - 3 == 0 - 3 + 2 * 4 ) {

    } else {
        print "Issues with PLUSMINUS, MULTIPLY and EQUALEQUAL{";
    }

}
