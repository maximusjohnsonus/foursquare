# foursquare
Foursquare is a four dimensional game. Right now, it doesn't have any game aspect, but you can walk around in four dimensions!

Foursquare renders four dimensions by taking a three dimensional "slice" of a four dimensional world.

Fourbjects (fbj's) refer to four dimension objects. Likewise, Threebjects (tbj's) are three dimensional objects, the "faces" of fbj's.
I use x,y,z,w as my four coordinates. Those correspond with forward, right, up, and oot.

Fourbject and Threebject documentation:
Foubjects (e.g. a tesseract) contain three important fields: points[] (for a tesseract, 16: (±1,±1,±1,±1)), edges[][] (32 for a tesseract), and threebjects[] (8 for a tesseract)
Points[] consists of a list are the four dimensional coordinates of all the corners of the fbj
Edges[][] is a list of the pairs of Points that are supposed to be connected by an edge. e.g. if the 0th and 1st points in points[] are supposed to be connected, edges[][] will start { {0,1}, ... }
Threebjects[] is a list of the tbj's that make up the "faces" of the fbj. On a tesseract, the 8 tbj's will be the 8 cubes of a tesseract
Each Threebject has one important field: edges[]
Edges[] is a list of the edges of the tbj. Its entries are the indices of edges in the fbj's edges[][] list. e.g. for a tesseract, the fbj will have 32 edges (so fbj.edges.length = 32). Each tbj in threebjects[] will have 8 edges, so tbj.edges will contain 8 indices that refer to the edges in fbj.edges

To draw a fbj:
1) Calculate the intercepts of every edge of the fbj
2) Calculate where each intercept lies according to the player's location and view direction
3) Draw the polygon that each tbj will create (if any)
4) ???
5) Profit

    First, we turn each edge (a 1D object in a 4D space) into a matrix equation of the form A*x=b. We calculate A (a 3×4 matrix) and b (a 3×1 matrix), and x is a variable vector in 4D (or a 4×1 matrix). We could solve Ax=b by rref-ing the matrix [A b], but that doesn't help us right now, because it would return the edge, which is a line segment in 4D. We need to "slice" the four-space so that we get a three-space that we can look at. When we slice a fourbject, we get a threebject. When we slice a threebject (i.e. a face of a fbj), we get a Twobject (polygon)*. When we slice a line segment, we get a point*. We need to slice the edge so that we get a point.
 *Depending on orientation, we may have no intersection between the object and the three dimensional slice, or the entire object may lie in the 3D slice. However, typically we will go down a dimension after slicing.
    To slice the edge, we need to have a description of the three-space with which we are slicing. We use three perpendicular vectors to do this. v1 is the unit vector defining the forward direction for the player, v2 is the unit vector defining the rightward direction, and v3 is the unit vector defining up. v1, v2, v3 are vectors in four-space. The only points the player can see (the points in the 3D slice) are in span(v1, v2, v3). Let V be the matrix [v1 v2 v3]. 
    We can now find the point on the edge that falls in the 3D viewing slice. We simply solve A*(V*y)=b. V*y takes a 3D vector y (the vector that describes where the player sees point y) and transforms it into the four dimensional world. If V*y solves A*x=b, then V*y is on the edge. To solve A*(V*y)=b, take rref( [A*V b] ). A is 3×4, V is 4×3, b is 3×1, so [A*V b] is 3×4. When rref-ed, we get a matrix of the form:
    ⎡1 0 0 a⎤
    ⎢0 1 0 b⎥
    ⎣0 0 1 c⎦
If we don't get the identity matrix on the left, there is either no intersection between the line and the 3D viewing space or the line lies entirely within said 3-space. In both cases, we can not draw anything (in the latter case, the line will still be defined by adjacent edges). When we do get the identity matrix, we know the point of intersection lies a units in front of the player, b units to the right, and c units above. We have to check that the point of intersection is actually on the line segment, not just the line; convert it into 4D using V and check that it's between the endpoints of the edge. We draw the point at (b/a, c/a) (we don't actually draw it, just the polygon it's part of. If we did draw it, it would be there though).
    Which points do we connect? That's where we use the tbj's. Each tbj makes a polygon when sliced, so for every tbj we go through edges[] and, if that edge has a valid intersection point, add the intersection point to a polygon. We have to reorder the points so they're convex, which is done using cross products. At this point, we can draw the polygon. For a tesseract, we have 8 tbj's, and usually 6 of them intersect with the slice, so we will draw 6 polygons. These 6 polygons are the faces of a cube.
