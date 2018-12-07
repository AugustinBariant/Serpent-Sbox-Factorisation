import java.util.ArrayList;
import java.util.List;

public class OptimizerSolver {
	private List<Optimizer> L;
	private int[] permutation;
	private Optimizer finalOptimizer;
	
	OptimizerSolver(int[] p){
		permutation = p;
		L = new ArrayList<Optimizer>();
		finalOptimizer = null;
	}
	
	public Optimizer solve(){
		L.add(new Optimizer(permutation));
		int m=0;
		while(true) {
			if(L.isEmpty()) {
				System.out.print("Liste Vide");
				break;
			}
			Optimizer o = L.get(0);
			L.remove(o);
			
			InstructionCycle cycle = new InstructionCycle();
			FullInstruction i = cycle.updateNewInstruction();
			//o.PrintCurrentState();
			while(!i.isEnd) {
				Optimizer save = o.copy();
				//System.out.print(Instruction.instr_names[el.instruct.Id] + "(" + el.column1 + "," + el.column2 +")\n");
				if(save.applyInstruction(i)) {
					//System.out.print("added instrct :" + i.StringToPrint());
					if(save.numberOfMatch==4) {
						System.out.print("\nSolution trouv�e en " + save.operations.size() + " op�rations\n");
						int k =0;
						for(FullInstruction el:save.operations) {
							k+=1;
							System.out.print("Instruction "+  k+ " : " + el.stringToPrint());
						}
						finalOptimizer = save;
						break;
					}
					L.add(save);
				}
				i = cycle.updateNewInstruction();
			}
			m+=1;
			System.out.print("\nEtape " + m + " termin�e, L est de longueur "+ L.size() + ", il y a " + o.operations.size() + " op�rations, avec " + o.numberOfMatch + " matchs\n tree est de longueur "+Optimizer.tree.size()+"\n");
			if(finalOptimizer!=null) {
				break;
			}
		}
		if(finalOptimizer!=null) {
			return finalOptimizer;
		}else {
			return null;
		}
		
	}
	
}
