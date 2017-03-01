package ru.rubicon.roma.picturefilters;

/**
 * Created by roma on 01.03.2017.
 */

public class CurtainFilter extends TransitionFilterImpl {
    private CurtainFilter(int framesCount, ActionFilter showFilter, ActionFilter hideFilter) {
        super(framesCount, showFilter, hideFilter);
    }

    public static final TransitionFilter getInstance(int framesCount){
        ActionFilter curtainActionFilter = new CurtainActionFilter(framesCount);
        ActionFilter slideInActionFilter = new SlideInActionFilter(framesCount, 0.25f);
        curtainActionFilter.setNextFilter(slideInActionFilter);
        return new CurtainFilter(framesCount, curtainActionFilter, null);
    }
}
