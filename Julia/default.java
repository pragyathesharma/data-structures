while( (zx*zx)+(zy*zy) < 6 && iter > 0)
				{
					double val = (zx*zx) - (zy*zy) + a;
					zy = (2*zx*zy) + b;
					zx = val;
					iter--;
				}