int findmax ( int[10] foo, int index ) {
        int max;
        int next_max;
        max = foo[index];
        if ( index < 10 ) {
                next_max = findmax(foo,index+1);
                if( max < next_max ) { max = nextmax; }                
        }       
        return max;
}

void main () {
        int[10] foo;
        int x;
        foo[0] = 1;
        foo[1] = 9;
        foo[2] = 22;
        foo[3] = 9;
        foo[4] = 11;
        foo[5] = 0-99;
        foo[6] = 11;
        foo[7] = 22;
        foo[8] = 22;
        foo[9] = 11;
        x = findmax(foo);
        println x;
}