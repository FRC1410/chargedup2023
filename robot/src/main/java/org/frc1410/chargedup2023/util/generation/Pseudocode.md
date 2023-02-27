# Overall goal
1. Decide if we are picking up from substation or scoring
2. From current camera position, generate trajectory to move to desired position
3. Follow trajectory while moving other mechanisms to achieve task

# Overall Steps
1. Can we see an AprilTag?
   1. Yes: Continue
   2. No: Return null or error condition
2. Is it a substation or grid tag?
   1. Substation: Generate substation pickup command
   2. Grid: Generate grid scoring command
   3. Also set flag for red or blue here
