.source ar.ul
.class public ar
.super java/lang/Object

.method public static __main()V
   .limit locals 8
   .var 0 is x [I from L_0 to L_1
   .var 1 is y I from L_0 to L_1
   .var 2 is T2 I from L_0 to L_1
   .var 3 is T3 I from L_0 to L_1
   .var 4 is T4 I from L_0 to L_1
   .var 5 is T5 I from L_0 to L_1
   .var 6 is T6 I from L_0 to L_1
   .var 7 is T7 I from L_0 to L_1
   .limit stack 16
L_0:
;  T0 := NEWARRAY I 3;
   ldc 3
   newarray int
   astore 0
;  T2 := 7;
   ldc 7
   istore 2
;  T3 := 0;
   ldc 0
   istore 3
;  T0[T3] := T2;
   aload 0
   iload 3
   iload 2
   iastore
;  T4 := 1;
   ldc 1
   istore 4
;  T1 := T4;
   iload 4
   istore 1
;  T5 := 2;
   ldc 2
   istore 5
;  T6 := 1;
   ldc 1
   istore 6
;  T7 := T1 I+ T6;
   iload 1
   iload 6
   iadd
   istore 7
;  T0[T7] := T5;
   aload 0
   iload 7
   iload 5
   iastore
;  RETURN;
   return
L_1:
.end method

; BOILERPLATE CODE

.method public static main([Ljava/lang/String;)V
   .limit locals 1
   .limit stack 4
   invokestatic ar/__main()V
   return
.end method

.method public <init>()V
   aload 0
   invokenonvirtual java/lang/Object/<init>()V
   return
.end method
