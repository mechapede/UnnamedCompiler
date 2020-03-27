.source foo2.ul
.class public foo2
.super java/lang/Object

.method public static __foo(II)V
   .limit locals 10
   .var 0 is x I from L_0 to L_1
   .var 1 is y I from L_0 to L_1
   .var 2 is z I from L_0 to L_1
   .var 3 is T3 I from L_0 to L_1
   .var 4 is T4 I from L_0 to L_1
   .var 5 is T5 I from L_0 to L_1
   .var 6 is T6 I from L_0 to L_1
   .var 7 is T7 I from L_0 to L_1
   .var 8 is T8 I from L_0 to L_1
   .var 9 is T9 Z from L_0 to L_1
   .limit stack 16
L_0:
;  T3 := 7;
   ldc 7
   istore 3
;  T4 := T3 I* T1;
   iload 3
   iload 1
   imul
   istore 4
;  T5 := T0 I+ T4;
   iload 0
   iload 4
   iadd
   istore 5
;  T6 := 2;
   ldc 2
   istore 6
;  T7 := T5 I+ T6;
   iload 5
   iload 6
   iadd
   istore 7
;  T2 := T7;
   iload 7
   istore 2
;  T8 := T0 I+ T1;
   iload 0
   iload 1
   iadd
   istore 8
;  T2 := T8;
   iload 8
   istore 2
;  T9 := T0 I== T1;
   iload 0
   iload 1
   isub
   ifeq L_2
   ldc 0
   goto L_3
L_2:
   ldc 1
L_3:
   istore 9
;  RETURN;
   return
L_1:
.end method

.method public static __foo2(FF)V
   .limit locals 9
   .var 0 is x F from L_0 to L_1
   .var 1 is y F from L_0 to L_1
   .var 2 is z F from L_0 to L_1
   .var 3 is T3 F from L_0 to L_1
   .var 4 is T4 F from L_0 to L_1
   .var 5 is T5 F from L_0 to L_1
   .var 6 is T6 F from L_0 to L_1
   .var 7 is T7 F from L_0 to L_1
   .var 8 is T8 F from L_0 to L_1
   .limit stack 16
L_0:
;  T3 := 7.0;
   ldc 7.0
   fstore 3
;  T4 := T3 F* T1;
   fload 3
   fload 1
   fmul
   fstore 4
;  T5 := T0 F+ T4;
   fload 0
   fload 4
   fadd
   fstore 5
;  T6 := 2.0;
   ldc 2.0
   fstore 6
;  T7 := T5 F+ T6;
   fload 5
   fload 6
   fadd
   fstore 7
;  T2 := T7;
   fload 7
   fstore 2
;  T8 := T0 F+ T1;
   fload 0
   fload 1
   fadd
   fstore 8
;  T2 := T8;
   fload 8
   fstore 2
;  RETURN;
   return
L_1:
.end method

.method public static __main()V
   .limit locals 4
   .var 0 is T0 I from L_0 to L_1
   .var 1 is T1 I from L_0 to L_1
   .var 2 is T2 F from L_0 to L_1
   .var 3 is T3 F from L_0 to L_1
   .limit stack 16
L_0:
;  T0 := 2;
   ldc 2
   istore 0
;  T1 := 4;
   ldc 4
   istore 1
;  CALL foo(T0 T1);
   iload 0
   iload 1
   invokestatic foo2/__foo(II)V
;  T2 := 2.0;
   ldc 2.0
   fstore 2
;  T3 := 9.0;
   ldc 9.0
   fstore 3
;  CALL foo2(T2 T3);
   fload 2
   fload 3
   invokestatic foo2/__foo2(FF)V
;  RETURN;
   return
L_1:
.end method

; BOILERPLATE CODE

.method public static main([Ljava/lang/String;)V
   .limit locals 1
   .limit stack 4
   invokestatic foo2/__main()V
   return
.end method

.method public <init>()V
   aload 0
   invokenonvirtual java/lang/Object/<init>()V
   return
.end method
