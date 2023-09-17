package escape.impl.movement.movevalidation.rules;

import escape.required.Rule;

// Implementation of the Rule interface
public class RuleImpl implements Rule {

    private RuleID ruleID;
    private int value = 0;

    public RuleImpl(RuleID ruleID){

        this.ruleID = ruleID;

    }

    @Override
    public RuleID getId() {
        return this.ruleID;
    }

    @Override
    public int getIntValue() {
        return this.value;
    }

    /*
    public void setRuleID(RuleID ruleID){
        this.ruleID = ruleID;
    }

     */

    public void setValue(int value){
        this.value = value;
    }

    /*
    @Override
    public String toString(){
        if(this.value == 0) {
            return ruleID + "";
        }
        else{
            return ruleID + " " + value;
        }
    }

     */
}
