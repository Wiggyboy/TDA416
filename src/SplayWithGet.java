import static java.lang.Integer.signum;

public class SplayWithGet<E extends Comparable<? super E>> extends BinarySearchTree<E> implements CollectionWithGet<E> {
	
	
	
	E foundElement;
	
	
	
	@Override
	public E get(E element) {
		foundElement = null;
		
		if (root != null) splayFind(root, element, 0);
		
		return foundElement;
	}
	
	
	
	private int splayFind(Entry e, E element, int depth) {
		int token = element.compareTo(e.element);
		Entry next = null;
		
		if (token == 0) {
			foundElement = e.element;
			return 0;
		}
		
		next = (token < 0) ? e.left : e.right;
		if (next == null) return 0;
		
		token = 3 * splayFind(next, element, depth+1) + signum(token);
		return (depth % 2 == 0) ? splay(e, token) : token;
	}
	
	
	
	private int splay(Entry e, int token) {
		
		if (token > 0) {
			
			if (token > 2) zigzig(e);		// 4
			else if (token < 2) zig(e);		// 1
			else zagzig(e);					// 2
			
		}
		else {
			
			if (token < -2) zagzag(e);		// -4
			else if (token > -2) zag(e);	// -1
			else zigzag(e);					// -2
			
		}
		
		return 0;
	}
	
	
	
	private void zagzag(Entry x) {
		Entry 	y = x.left,
				z = y.left,
				A = z.left,
				B = z.right,
				C = y.right,
				D = x.right;
		
		E e = x.element;
		x.element = z.element;
		z.element = e;
		
		z = x;
		x = y.left;
		
		// Puzzle it together.
		
		z.left = A;		if (A != null)	A.parent = z;
		z.right = y;					y.parent = z;
		
		y.left = B;		if (B != null)	B.parent = y;
		y.right = x;					x.parent = y;
		
		x.left = C;		if (C != null)	C.parent = x;
		x.right = D;	if (D != null)	D.parent = x;
	}
	
	

	private void zigzig(Entry x) {
		Entry 	y = x.right,
				z = y.right,
				A = x.left,
				B = y.left,
				C = z.left,
				D = z.right;
		
		E e = x.element;
		x.element = z.element;
		z.element = e;
		
		z = x;
		x = y.right;
		
		// Puzzle it together.
		
		z.left = y;						y.parent = z;
		z.right = D;	if (D != null)	D.parent = z;
		
		y.left = x;						x.parent = y;
		y.right = C;	if (C != null)	C.parent = y;
		
		x.left = A;		if (A != null)	A.parent = x;
		x.right = B;	if (B != null)	B.parent = x;
	}
	
	
	
	/* Rotera 1 steg i hogervarv, dvs 
		    x'                 y'
		   / \                / \
		  y'  C   -->        A   x'
		 / \                    / \  
		A   B                  B   C
	*/
	private void zag( Entry x ) {
		Entry   y = x.left;
		E    temp = x.element;
		x.element = y.element;
		y.element = temp;
		x.left    = y.left;
		if ( x.left != null )
			 x.left.parent   = x;
		y.left    = y.right;
		y.right   = x.right;
		if ( y.right != null )
			 y.right.parent  = y;
		x.right   = y;
	}
	
	
	
	//   rotateRight
	// ========== ========== ========== ==========
	
	/* Rotera 1 steg i vanstervarv, dvs 
	    x'                 y'
	   / \                / \
	  A   y'  -->        x'  C
	     / \            / \  
	    B   C          A   B   
	*/
	private void zig( Entry x ) {
		Entry  y  = x.right;
		E temp    = x.element;
		x.element = y.element;
		y.element = temp;
		x.right   = y.right;
		if ( x.right != null )
			 x.right.parent  = x;
		y.right   = y.left;
		y.left    = x.left;
		if ( y.left != null )
			 y.left.parent   = y;
		x.left    = y;
	}
	
	
	
	//   rotateLeft
	// ========== ========== ========== ==========
	
	/* Rotera 2 steg i hogervarv, dvs 
	    x'                  z'
	   / \                /   \
	  y'  D   -->        y'    x'
	 / \                / \   / \
	A   z'             A   B C   D
	   / \  
	  B   C  
	*/
	private void zagzig( Entry x ) {
		Entry   y = x.left,
		z = x.left.right;
		E       e = x.element;
		x.element = z.element;
		z.element = e;
		y.right   = z.left;
		if ( y.right != null )
			y.right.parent = y;
		z.left    = z.right;
		z.right   = x.right;
		if ( z.right != null )
			z.right.parent = z;
		x.right   = z;
		z.parent  = x;
	}
	
	
	
	//  doubleRotateRight
	// ========== ========== ========== ==========
	
	/* Rotera 2 steg i vanstervarv, dvs 
	    x'                  z'
	   / \                /   \
	  A   y'   -->       x'    y'
	     / \            / \   / \
	    z   D          A   B C   D
	   / \  
	  B   C  
	*/
	private void zigzag( Entry x ) {
		Entry  y  = x.right,
		z  = x.right.left;
		E      e  = x.element;
		x.element = z.element;
		z.element = e;
		y.left    = z.right;
		if ( y.left != null )
			y.left.parent = y;
		z.right   = z.left;
		z.left    = x.left;
		if ( z.left != null )
			z.left.parent = z;
		x.left    = z;
		z.parent  = x;
	}
	
	
	
}
