
!has(dirt).

!cleanRoom(dirt).

+!cleanRoom(dirt): has(dirt) <- .wait(400);
									!at(robotHos,dirt);
									.print("Cleaned Room!");
									!at(robotHos, init);
									!cleanRoom(dirt).

+!cleanRoom(dirt): not has(dirt) <- .wait(5);
										!cleanRoom(dirt).
										
+!at(robotHos,P) : at(robotHos,P) <- true.
+!at(robotHos,P) : not at(robotHos,P)
    <- move(robotHos, P);
       !at(robotHos,P).

-!at(_,_)
   :  true
   <- true.

-!has(dirt)
   :  true
   <- .random(X); .wait(X*5000+2000);
      !has(dirt).
