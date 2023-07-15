import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import sys
sys.stdout.reconfigure(encoding='utf-8')
product_id = int(sys.argv[1])
# Đọc dữ liệu từ file CSV
data = pd.read_csv(
    'C:/Users/Duy/Documents/HocTap/TLCN/TDGymStore/data/dataContent.csv')

# Tạo vector từ các thuộc tính nameProduct, description và categoryName
vectorizer = TfidfVectorizer()
corpus = data['name'] + ' ' + data['description'] + ' ' + data['categoryName']
description_matrix = vectorizer.fit_transform(corpus)

# Tính ma trận độ tương đồng dựa trên cosine similarity
similarity_matrix = cosine_similarity(description_matrix)


def convert_array_to_string(arr):
    # Sử dụng phương thức join() để kết hợp các phần tử của mảng thành một chuỗi
    # và sử dụng ", " làm dấu phân tách giữa các phần tử
    string = ", ".join(map(str, arr))
    return string

# Hàm đề xuất sản phẩm dựa trên id sản phẩm đầu vào


def recommend_products(product_id, top_n):
    # Lấy chỉ số của sản phẩm trong dataset dựa trên id
    index = data[data['id'] == product_id].index[0]

    # Lấy vector tương đồng của sản phẩm đầu vào
    similarities = similarity_matrix[index]

    # Sắp xếp các sản phẩm dựa trên mức độ tương đồng
    similar_indices = similarities.argsort()[::-1]

    # Lấy top N sản phẩm tương đồng nhất
    top_indices = similar_indices[1:top_n+1]

    # Lấy danh sách điểm tương đồng
    similarity_scores = similarities[top_indices]

    # Trả về danh sách các sản phẩm được đề xuất
    recommended_products = data.loc[top_indices, 'id'].values
    return recommended_products


# Sử dụng hàm recommend_products để đề xuất sản phẩm
# product_id = 5  # Id của sản phẩm đầu vào
top_n = 8  # Số lượng sản phẩm được đề xuất
recommendations = recommend_products(product_id, top_n)
print(convert_array_to_string(recommendations))
