package aggregate.core.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import aggregate.core.constant.BaseSheetHeader;
import aggregate.core.constant.Constant;
import aggregate.core.model.ColumnSet;

/**
 * CSVファイルのデータを読み込むクラス
 */
@Component
public class CsvReader {

  /**
   * 指定されたファイルの指定ヘッダーを読み込んで{@link ColumnSet}のListを返す
   *
   * @param headers ヘッダーの配列
   * @param filePath CSVファイルの絶対パス
   * @return {@link ColumnSet}のList
   */
  public List<ColumnSet> read(BaseSheetHeader[] headers, String filePath) {
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      String line = br.readLine();
      // read header
      List<BaseSheetHeader> fileHeaders = readHeader(headers, line.split(Constant.COMMA));
      // read body
      List<ColumnSet> result = new ArrayList<>();
      while ((line = br.readLine()) != null) {
        result.add(new ColumnSet(fileHeaders, line.split(Constant.COMMA)));
      }
      return result;
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private List<BaseSheetHeader> readHeader(BaseSheetHeader[] headers, String[] values) {
    return Arrays.asList(values).stream().map(value -> getHeaderByHeaderName(headers, value))
        .collect(Collectors.toList());
  }

  private BaseSheetHeader getHeaderByHeaderName(BaseSheetHeader[] headers, String headerName) {
    Optional<BaseSheetHeader> result = Arrays.stream(headers)
        .filter(header -> header.getHeaderName().equals(headerName)).findFirst();
    if (result.isPresent()) {
      return result.get();
    } else {
      throw new RuntimeException("Header is not found: " + headerName);
    }
  }

}
