/* abc - The AspectBench Compiler
 * Copyright (C) 2004 Ganesh Sittampalam
 *
 * This compiler is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This compiler is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this compiler, in the file LESSER-GPL;
 * if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */

package abc.weaving.weaver;

import abc.weaving.tagkit.InstructionKindTag;
import abc.weaving.tagkit.InstructionShadowTag;
import abc.weaving.tagkit.InstructionSourceTag;
import abc.weaving.tagkit.TagContainer;

/** When weaving a piece of advice, various values may need to be
 *  copied into variables and be passed as parameters to the advice
 *  body. The weaving context is passed to residues and to the advice
 *  declaration itself during weaving. 
 *  Each kind of advice will use a specific type of weaving context, and both
 * {@link abc.weaving.aspectinfo.AbstractAdviceDecl.makeAdviceExecutionStatements} 
 * and the {@link abc.weaving.residues.WeavingVar} instances generated by the
 * {@link abc.weaving.matching.WeavingEnv} for that advice kind will know what type to expect.
 * This class provides a base for the hierarchy of weaving contexts, as well as being appropriate
 * to use for any kind of advice that does not need any information to be passed to it. 
 *  @author Ganesh Sittampalam
 */

public class WeavingContext implements TagContainer {

    
    /* TagContainer implementation */
    private InstructionShadowTag shadowTag = null;
    private InstructionSourceTag sourceTag = null;
    private InstructionKindTag kindTag = null;

    public InstructionKindTag getKindTag() {
        return this.kindTag;
    }

    public void setKindTag(InstructionKindTag kindTag) {
        this.kindTag = kindTag;
    }

    public InstructionShadowTag getShadowTag() {
        return this.shadowTag;
    }

    public void setShadowTag(InstructionShadowTag shadowTag) {
        this.shadowTag = shadowTag;
    }

    public InstructionSourceTag getSourceTag() {
        return this.sourceTag;
    }

    public void setSourceTag(InstructionSourceTag sourceTag) {
        this.sourceTag = sourceTag;
    }
}
