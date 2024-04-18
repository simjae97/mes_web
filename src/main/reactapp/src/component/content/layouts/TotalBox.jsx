
export default function TotalBox(props){
    return(
        <div className="statistics">
            <h3>통계</h3>
            <div className="statisticsWrap">
                <div className="statisticsBox">
                    <div>진행전</div>
                    
                </div>
                <div className="statisticsBox">
                    <div className="statisticsBoxFont">진행중</div>
                    <div id="statisticsBoxFont">0개</div> 
                </div>
                <div className="statisticsBox">
                    <div className="statisticsBoxFont">완료</div> 
                    <div id="statisticsBoxFont">0개</div>
                </div>
                <div className="statisticsBox">
                    <div className="statisticsBoxFont">불합격</div>
                    <div id="statisticsBoxFont">0개</div> 
                </div>
            </div>
        </div>
    )
}